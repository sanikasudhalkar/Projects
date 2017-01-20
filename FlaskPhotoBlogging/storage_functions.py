from flask.ext.pymongo import PyMongo
import pymongo
mongo = PyMongo()
from flask import session, flash
import ipdb
from bson.objectid import ObjectId
#mongo = pymongo.MongoClient('mongodb://104.197.6.30:27017/database')

picList = list
def init_app(app):
  mongo.init_app(app)

def getImage():
    image = mongo.db.pics.find_one({"_id" : ObjectId("57759182a9ca955a67aa8391")})
    return image

def is_valid_user(username, password):

    user = mongo.db.login.find_one({"username": username, "password": password})
    if not user:
        return False
    return True

def add_pic_to_db(pic_data):
    count = mongo.db.pics.count({"username": session["username"]})
   # ipdb.set_trace()
    if count==10:
        flash("You have exceeded your image upload limit!!")
        return None
    else:
        id = mongo.db.pics.insert(pic_data)
        return id

def get_pics(username):
    if username is not None:
        results = mongo.db.pics.find({"username":username})
    else:
        results = mongo.db.pics.find()
    pics = picList(map(get_pic_list, results))
    return pics

def get_pic_list(pic):
    if not pic:
        return None
    return pic

def get_text(word):
   #mongo.db.docs.create_index({"text": "text"})
   mongo.db.set
   results = mongo.db.docs.find({"$text": {"$search":word}} )

   files = picList(map(get_pic_list, results))

   return files

def delete_pic(id):
    if not isinstance(id, ObjectId):
        id = ObjectId(id)
    picUrl = mongo.db.pics.find_one(id)["picUrl"]
    mongo.db.pics.remove(id)
    return picUrl