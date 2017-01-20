from flask import Blueprint, redirect, render_template, request, url_for, session
from storage_functions import is_valid_user, add_pic_to_db, get_pics, delete_pic, get_text, getImage
import ipdb
from gcloud import storage
import six
from werkzeug import secure_filename
import datetime

picbook = Blueprint('picbook', __name__)

@picbook.route("/", methods=["GET", "POST"])
def find_file():
    if request.method == "POST":
        fileinfo = request.form.to_dict(flat=True)
        image = getImage()
        start = datetime.datetime.now()
        pics = get_text(fileinfo['word'])
        end = datetime.datetime.now()-start

        return render_template("show_pics.html", pics = pics, image = image, time = str(end))
    return render_template("find.html")


@picbook.route("/addPic.html", methods = ["GET","POST"])
def add_pic():
    if request.method=="POST":
        pic_data = request.form.to_dict(flat=True)
        pic_url = store_in_bucket(request.files.get('pic'))
        #ipdb.set_trace()
        if pic_url:
            pic_data['picUrl'] = pic_url

        pic_data['username']=session['username']
        add_pic_to_db(pic_data)
    return render_template("addPic.html")

@picbook.route("/show_my_pics")
def my_pics():
    pics = get_pics(session["username"])
    return render_template("show_pics.html", pics=pics)

@picbook.route("/show_all_pics")
def all_pics():
    pics = get_pics(None)
    return render_template("show_pics.html", pics=pics)

@picbook.route("/delete/<id>")
def delete(id):
    picUrl = delete_pic(id)
    delete_from_storage(picUrl)
    pics = get_pics(session["username"])
    return render_template("show_pics.html", pics=pics)


def store_in_bucket(file):
    """
    Upload the user-uploaded file to Google Cloud Storage and retrieve its
    publicly-accessible URL.
    """
    if not file:
        return None

    client = storage.Client(project='sanikacloud')
    bucket = client.get_bucket('sanikacloud')
    filename = secure_filename(file.filename)
    date = datetime.datetime.utcnow().strftime("%Y-%m-%d-%H%M%S")
    basename, extension = filename.rsplit('.', 1)
    blob = bucket.blob("{0}-{1}.{2}".format(basename, date, extension))

    #ipdb.set_trace()
    blob.upload_from_string(
        file.read(),
        content_type=file.content_type)

    url = blob.public_url

    if isinstance(url, six.binary_type):
        url = url.decode('utf-8')

    return url

def delete_from_storage(picUrl):
    client = storage.Client(project='sanikacloud')
    bucket = client.get_bucket('sanikacloud')
    a,b,c,d,e=picUrl.split('/')

    bucket.delete_blob(e,client)

