from flask import current_app, Flask, redirect, url_for, render_template
from storage_functions import init_app
from picBook import picbook
from gcloud import storage
import six

app = Flask(__name__)
#Only allow upto 8mb file size
app.config['MAX_CONTENT_LENGTH'] = 8 * 1024 * 1024
app.register_blueprint(picbook)
app.secret_key = "secret"
app.config.from_object("appengine_config")
with app.app_context():
    init_app(app)
# Add a default root route.
@app.route('/')
def index():
    return render_template("login.html")

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)



