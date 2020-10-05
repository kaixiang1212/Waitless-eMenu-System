import json

from flask import request, render_template, redirect, url_for, send_file

from server import app, mDB, MM
from src.forms import New_Item_Form, New_Category_Form

# app routes for management FRONTEND - not part of API
from src.helpers import save_file, get_categories


@app.route('/management', methods=["GET", "POST"])
def index():
    return redirect(url_for('view_categories'))


@app.route('/management/add_item', methods=['GET', 'POST'])
def add_item():
    if request.method == "GET":
        form = New_Item_Form()
        form.category.choices = get_categories()

        return render_template('add_item.html',
                               categories=mDB.get_categories(sort=True), form=form)

    elif request.method == "POST":
        item_name = request.form['title']
        item_description = request.form['description']
        price = request.form['price']
        categories = request.form.getlist('category')
        ingredient = request.form['ingredient'].split(',')
        file = request.files['image']

        filename = save_file(file)
        if filename is not None:
            # Valid filename
            MM.add_item(item_name, item_description, price, ingredient, categories, filename)

            return render_template("message.html",
                                   categories=mDB.get_categories(sort=True),
                                   header="Item Added",
                                   page_header="Item Added",
                                   message="Item has been added successfully")
        else:
            # Invalid Filename
            return render_template("message.html",
                                   categories=mDB.get_categories(sort=True),
                                   header="Item Not Added",
                                   page_header="Invalid Filename",
                                   message="")
    # neither GET nor POST
    return ""


# class name 'add_category' causes problem for some reason
@app.route('/management/add_category', methods=['GET', 'POST'])
def add_categoryy():
    if request.method == "GET":
        form = New_Category_Form()
        return render_template('add_category.html',
                               categories=mDB.get_categories(sort=True),
                               form=form)

    elif request.method == 'POST':
        category_name = request.form['category_name']
        if MM.add_category(category_name) is None:
            # category with same name is already exist
            return render_template('message.html',
                                   categories=mDB.get_categories(sort=True),
                                   header="Error",
                                   page_header="Error Adding Category",
                                   message="A category already exists with the name \"" + category_name + "\"")
        else:
            return render_template('message.html',
                                   categories=mDB.get_categories(sort=True),
                                   header="Add Category",
                                   page_header="Category Added",
                                   message="A new category named \"" + category_name + "\" has been added")
    # neither GET nor POST
    return ""


@app.route('/management/items')
def view_items():
    return render_template('view_items.html',
                           categories=mDB.get_categories(sort=True),
                           items=mDB.get_items())


@app.route('/management/items/<not_obj_id>', methods=["GET", "POST"])
def edit_item(not_obj_id):
    item = mDB.get_item_by_name(not_obj_id)
    if item is None:
        return "Item not found"
    elif request.method == "GET":
        form = New_Item_Form()
        form.category.choices = get_categories()
        form.title.data = item['name']
        form.category.data = mDB.get_item_tag_list_str(item['name'])
        form.price.data = item['cost']
        form.description.data = item['description']
        return render_template('edit_item.html',
                               categories=mDB.get_categories(sort=True),
                               form=form,
                               ingredients=item['ingredients'])
    elif request.method == "POST":
        if request.form['submit'] == "Delete item":
            MM.remove_item(item['name'])
        else:
            item_name = request.form['title']
            item_description = request.form['description']
            price = request.form['price']
            categories = request.form.getlist('category')
            ingredients = request.form['ingredient'].split(',')

            MM.edit_item(item['name'], item_name, item_description, price, ingredients, categories)

        return redirect(url_for('view_items'))


@app.route('/management/categories')
def view_categories():
    return render_template('view_categories.html',
                           categories=mDB.get_categories(sort=True),
                           cats=mDB.get_all_items_grouped_by_category())


@app.route('/management/categories/<category_name>')
def re_order(category_name):
    if request.method == "GET":
        return render_template('sort_category_item.html',
                               categories=mDB.get_categories(sort=True),
                               category_name=category_name,
                               category_items=mDB.get_items_by_category_as_array(category_name))

    elif request.method == 'POST':
        # neither GET nor POST
        return ""
    return ""


@app.route('/frontend/index.js')
def script():
    return open("frontend/index.js", "r").read()


@app.route('/style/index.css')
def style():
    return open("templates/management_index.css", "r").read()


@app.route('/testing')
def testing():
    for c in mDB.get_tags():
        print(c)
    return ""

@app.route('/images/<string:image_name>.jpg')
def serve_image(image_name):
    filename = 'static/uploads/' + image_name + '.jpg'
    return send_file(filename, mimetype='image/jpg')

@app.route('/images/<string:image_name>.png')
def serve_image2(image_name):
    filename = 'static/uploads/' + image_name + '.png'
    return send_file(filename, mimetype='image/png')