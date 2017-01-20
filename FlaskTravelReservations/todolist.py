"""
Routes and views for the flask application.
"""

from datetime import datetime
from flask import render_template, request, session, url_for, redirect
from FlaskWebProject import app
from storage import is_valid_user, add_note_to_db, delete_note, find_fares_for_date, find_fares_for_start, find_fares_for_range #, edit_note, fetch_note
import base64
import ipdb

@app.route("/", methods=["GET","POST"])
def login():
    if request.method == 'POST':
        username = request.form["username"]
        password = request.form["password"]
        userLevel = is_valid_user(username,password)
        if userLevel is not None:
            session["username"] = username
            session["level"] = userLevel
            return redirect(url_for('find_fares'))

    return render_template("login.html")

@app.route("/findFares", methods = ["GET","POST"])
def find_fares():
    if request.method == "POST":
        source = request.form["source"]
        destination = request.form["destination"]
        date = request.form["date"]
        if source == "Dallas":
            source = "DAL"
        if source == "Austin":
            source = "AUS"
        if source == "Boston":
            source = "BOS"
        if destination == "Dallas":
            destination = "DAL"
        if destination == "Austin":
            destination = "AUS"
        if destination == "Boston":
            destination = "BOS"

        #ipdb.set_trace()
        fares = find_fares_for_date(source, destination, date)
        #ipdb.set_trace()
        return render_template("showFares.html",fares=fares)
    return render_template("findFares.html")

@app.route("/findFaresStarting", methods = ["GET","POST"])
def find_fares_starting():
    if request.method == "POST":
        source = request.form["source"]
        date = request.form["date"]
        if source == "Dallas":
            source = "DAL"
        if source == "Austin":
            source = "AUS"
        if source == "Boston":
            source = "BOS"


        #ipdb.set_trace()
        fares = find_fares_for_start(source, date)
        #ipdb.set_trace()
        return render_template("showFares.html",fares=fares)
    return render_template("findFares.html")

@app.route("/findFaresRange", methods = ["GET","POST"])
def find_fares_range():
    if request.method == "POST":
        start_date = request.form["startDate"]
        end_date = request.form["endDate"]



        #ipdb.set_trace()
        fares = find_fares_for_range(start_date, end_date)
        #ipdb.set_trace()
        return render_template("showFares.html",fares=fares)
    return render_template("findFares.html")
#@app.route("/viewFares", methods = ["GET","POST"])
#def view_fares():
   # notes = fetch_notes(session["username"])
    #return render_template("showFares.html", notes=notes)





@app.route("/addNote", methods = ["GET","POST"])
def add_note():
    if request.method=="POST":
        note_data = request.form.to_dict(flat=True)
        image = request.files.get('image')
        ipdb.set_trace()
        if image is not None:
            enc_image = base64.b64encode(image.read())
            note_data['image'] = enc_image
        note_data['username']=session['username']
        time = add_note_to_db(note_data)
    return render_template("addNote.html")

@app.route("/addFares", methods = ["GET","POST"])
def add_fares():
    return "hello"
