from functools import wraps
import json
import os

from dotenv import Dotenv
from flask import Flask
from flask import redirect
from flask import render_template
from flask import request, Response
from flask import send_from_directory
from flask import session
import requests
import constants
import subprocess

env = None

try:
    env = Dotenv('./.env')
except IOError:
    env = os.environ

app = Flask(__name__, static_url_path='')
app.secret_key = constants.SECRET_KEY
app.debug = True


# Requires authentication annotation
def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        if constants.PROFILE_KEY not in session:
            return redirect('/')
        return f(*args, **kwargs)

    return decorated


@app.after_request
def apply_caching(response):
    response.headers.add('Cache-Control', 'no-store, no-cache, must-revalidate, post-check=0, pre-check=0')
    return response


# Controllers API
@app.route('/')
def home():
    return render_template('home.html', env=env)


@app.route('/upload_file')
@requires_auth
def uploadFile():
    return render_template('uploadFile.html', user=session[constants.PROFILE_KEY]["name"])


@app.route('/upload_success', methods=['GET', 'POST'])
@requires_auth
def uploadSuccess():
    file_to_analyze = request.files['file']
    user = session[constants.PROFILE_KEY]["name"]

    if (file_to_analyze != None):
        file_name = file_to_analyze.filename
        data_path = "/home/ubuntu/" + user + "_data"
        if not os.path.exists(data_path):
            os.makedirs(data_path)
        os.chdir(data_path)
        print str(file_name)
        if (str(file_name).endswith(".c") or file_name.endswith(".cpp")):
            # REMOVE THE OLD FILE AND RESULTS BEFORE ADDING THE NEW ONE
            delete_old_files(data_path, file_name, file_to_analyze)
            print "BEFORE SUBPROCESS OPENS!!!!"
            print "DATA PATH: " + "flawfinder " + data_path + "/" + file_name + " > " + data_path + "/output.txt"
            subprocess.Popen(
                "flawfinder " + data_path + "/" + file_name + " > " + data_path + "/output.txt", shell=True)
            return render_template("uploadSuccess.html")
        elif (str(file_name).endswith(".py")):
            # REMOVE THE OLD FILE AND RESULTS BEFORE ADDING THE NEW ONE
            delete_old_files(data_path, file_name, file_to_analyze)
            subprocess.Popen(
                "pylint " + data_path + "/" + file_name + " > " + data_path + "/output.txt", shell=True)
            return render_template("uploadSuccess.html")
        else:
            return render_template("notSuccessful.html")

    return render_template("notSuccessful.html")



def delete_old_files(data_path, file_name, file_to_analyze):
    for file in os.listdir(data_path):
        file_path = os.path.join(data_path, file)
        os.remove(file_path)
    file_to_analyze.save(os.path.join(data_path, file_name))

""" AUTH0 was used to provide login functionality. The following code has been taken from
https://auth0.com/docs/quickstart/backend/python"""
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
@app.route('/view_result')
@requires_auth
def viewResult():
    user = session[constants.PROFILE_KEY]["name"]
    if(os.path.exists("/home/ubuntu/" + user + "_data/output.txt")):
        with open("/home/ubuntu/" + user + "_data/output.txt") as output:
            output_text = output.read()
    else:
        output_text= "No results to show!!"
    return render_template('viewResult.html', user=session[constants.PROFILE_KEY]["name"], output=output_text)


@app.route('/public/<path:filename>')
def static_files(filename):
    return send_from_directory('./public', filename)



@app.route('/callback', methods=['GET'])
def callback_handling():
    code = request.args.get(constants.CODE_KEY)
    json_header = {constants.CONTENT_TYPE_KEY: constants.APP_JSON_KEY}
    token_url = 'https://{auth0_domain}/oauth/token'.format(auth0_domain=env[constants.AUTH0_DOMAIN])
    token_payload = {
        constants.CLIENT_ID_KEY: env[constants.AUTH0_CLIENT_ID],
        constants.CLIENT_SECRET_KEY: env[constants.AUTH0_CLIENT_SECRET],
        constants.REDIRECT_URI_KEY: env[constants.AUTH0_CALLBACK_URL],
        constants.CODE_KEY: code,
        constants.GRANT_TYPE_KEY: constants.AUTHORIZATION_CODE_KEY
    }

    token_info = requests.post(token_url, data=json.dumps(token_payload),
                               headers=json_header).json()

    user_url = 'https://{auth0_domain}/userinfo?access_token={access_token}'.format(
        auth0_domain=env[constants.AUTH0_DOMAIN], access_token=token_info[constants.ACCESS_TOKEN_KEY])
    user_info = requests.get(user_url).json()
    session[constants.PROFILE_KEY] = user_info
    return redirect('/upload_file')

""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

@app.route('/logout')
@requires_auth
def logout():
    print "logging out..."
    session.pop(constants.PROFILE_KEY, None)
    logout = requests.get("https://sanikasudhalkar.auth0.com/v2/logout")
    return render_template('logout.html')


if __name__ == '__main__':
    app.run(host='ec2-35-164-166-43.us-west-2.compute.amazonaws.com', debug=True)
