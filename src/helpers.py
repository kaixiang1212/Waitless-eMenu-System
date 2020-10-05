import os

from bson import ObjectId
from werkzeug.utils import secure_filename

from server import app, ALLOWED_EXTENSIONS, mDB


# src.helpers.py: def save_file()
# Checks if the file has the allowed extension
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


# routes.app.py: def add_item()
# Checks if a file is valid return null if invalid
# save file to uploads if valid
def save_file(file):
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        extension = filename.split('.')[-1]
        filename = "".join(filename.split('.')[:-1])

        # To avoid overriding file with same name
        file_index = 1
        temp_filename = filename + "." + extension
        while os.path.exists(os.path.join(app.config['UPLOAD_FOLDER'] + "/" + temp_filename)):
            temp_filename = filename + "({})".format(str(file_index)) + "." + extension
            file_index += 1

        file.save(os.path.join(app.config['UPLOAD_FOLDER'], temp_filename))
        return temp_filename
    return None


# routes.app.py: def add_item()
# For multi select form use
# each category option is a tuple
# ("Value", "Display Name")
def get_categories():
    categories = []
    for category in mDB.get_categories():
        categories.append((category['name'], category['name']))
    return categories


def serialise_obj(dct):
    data = {}
    for f in dct:
        if isinstance(dct[f], ObjectId):
            data[f] = str(dct[f])
        else:
            data[f] = dct[f]
    return data

