
import pymongo
from flask import session, flash
import datetime
from bson.objectid import ObjectId
import ipdb

def db_connection():
    client=pymongo.MongoClient(host="mongodb://ds040489.mlab.com:40489")
    db = client.sanikaclouddb
    db.authenticate("sanika","sanika")
    return db

db = db_connection()
faresList = list
def is_valid_user(username, password):

    level = db.login.find_one({"username": username, "password": password})["level"]
    if not level:
        return None
    return level

def find_fares_for_date(source, destination, date):
    fares = db.fares.find({"From":source,"to":destination,"date":date}).sort("Fare",pymongo.ASCENDING)

    if fares is not None:
        return faresList(map(get_note_list, fares))

    else:
        return None
def find_fares_for_start(source, date):
    fares = db.fares.find({"From":source,"date":date}).sort("Fare",pymongo.ASCENDING)

    if fares is not None:
        return faresList(map(get_note_list, fares))

    else:
        return None
def find_fares_for_range(start_date, end_date):
    fares = db.fares.find({"$and": [{ "$where": "this.date>=" +start_date +""}, {"$where": "this.date<=" +end_date +""}]} )
    if fares is not None:
        return faresList(map(get_note_list, fares))

    else:
        return None
def add_note_to_db(pic_data):
    count = db.notes.count({"username": session["username"]})
   # ipdb.set_trace()
    if count==20:
        flash("You have exceeded your note upload limit!!")
        return None
    else:
        uploadTime = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        pic_data['uploadTime']=uploadTime
        id = db.notes.insert(pic_data)
        return uploadTime

def fetch_notes(username, criteria):
    order = pymongo.ASCENDING
    if criteria == "Time & Date (descending)":
        order = pymongo.DESCENDING
        criteria = "uploadTime"
    if criteria == "Time & Date (ascending)":
        criteria="uploadTime"
    if username is not None:
        results = db.notes.find({"username": username}).sort(criteria,order)
    else:
        results = db.notes.find()
    #pics = notesList(map(get_note_list, results))
  #  return pics




def get_note_list(pic):
    if not pic:
        return None
    return pic

def delete_note(id):
    if not isinstance(id, ObjectId):
        id = ObjectId(id)
    doc = db.notes.find_one(id)
    db.notes.remove(id)


#def edit_note(id):
 #   if not isinstance(id, ObjectId):
  #    id = ObjectId(id)
   # db.notes.update_one({"_id":id} ,{"$set":{password:"sanika"}})
