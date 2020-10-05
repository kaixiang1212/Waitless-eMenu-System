import os

from flask import Flask
from flask_restx import Api

from src.MenuManager import MenuManager
from src.database import MenuDB
from src.assistance import AssistanceQueue

cwd = os.getcwd()

UPLOAD_FOLDER = cwd + u"/static/uploads"
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}

app = Flask(__name__)
app.secret_key = 'secret-key-123456'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.jinja_env.add_extension('jinja2.ext.loopcontrols')

api = Api(app=app)
mDB = MenuDB()
MM = MenuManager(mDB)
AQ = AssistanceQueue()
# assistance testing data
# AQ._q.append(5)
# AQ._q.append(1)
# AQ._q.append(2)