"""
The flask application package.
"""


from flask import Flask
import storage
app = Flask(__name__)
#Only allow upto 8mb file size
app.config['MAX_CONTENT_LENGTH'] = 8 * 1024 * 1024
app.secret_key = "secret"
import todolist

