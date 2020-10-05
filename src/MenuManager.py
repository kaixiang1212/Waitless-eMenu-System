from time import sleep

import pymongo

from src.database import MenuDB, F


class MenuManager:
    """
    This class handles all the Menu's logic previously from app.py
     - Item
     - Category
     - Tags (AKA Item-Category link)
    """

    def __init__(self, database: MenuDB):
        self._db = database

    # ========================================== Item =============================================

    def add_item(self, name, description, cost, ingredients, cat_names, imagePath):
        ingredients = [] if ingredients == [''] else ingredients
        data = {
            F.item.name: name,
            F.item.cost: cost,
            F.item.ingredients: ingredients,
            F.item.img_path: imagePath,
            F.item.desc: description
        }
        self._db.items.insert_one(data)
        for x in cat_names:
            self.add_tag(name, x)

    def edit_item(self, old_name, name, description, cost, ingredients, cat_names):
        ingredients = [] if ingredients == [''] else ingredients
        data = {'$set': {
            F.item.name: name,
            F.item.cost: cost,
            F.item.ingredients: ingredients,
            F.item.desc: description
        }}
        self._db.items.update_one({F.item.name: old_name}, data)
        if old_name != name:
            # update item-category links to the new item name
            self._db.tags.update_many({F.tag.item: old_name}, {'$set': {F.tag.item: name}})
        self.update_item_tags(name, cat_names)

    def remove_item(self, item_name):
        # remove item-category links then the item
        self.remove_tag(item_name, None)
        self._db.items.delete_one({F.item.name: item_name})

    # ========================================== Category =============================================

    def add_category(self, category_name):
        if self._db.get_category({F.cat.name: category_name}):
            return False
        priority = self._db.cats.count_documents({}) + 1

        self._db.cats.insert_one({F.cat.name: category_name, F.cat.priority: priority})
        return True

    def edit_category_priority(self, category_name, priority):
        category = self._db.get_category({F.cat.name: category_name})
        if category is None:
            return False
        self._db.cats.update_one(category, {'$set': {F.cat.priority: priority}})
        return True

    def remove_category(self, cat_name):
        # remove item-category links then the category
        self.remove_tag(None, cat_name)
        self._db.cats.delete_one({F.cat.name: cat_name})

    # ========================================== Tags =============================================

    def add_tag(self, item_name, cat_name):
        priority = self._db.tags.count_documents({F.tag.cat: cat_name}) + 1
        data = {
            F.tag.item: item_name,
            F.tag.cat: cat_name,
            F.tag.priority: priority
        }
        self._db.tags.insert_one(data)

    def edit_tag(self, tag_id, priority):
        self._db.tags.update_one({F.tag.id_: tag_id}, {'$set': {F.tag.priority: priority}})

    def edit_tag_priority(self, item, category, priority):
        tag = self._db.find_tag(item, category)
        self.edit_tag(tag[F.tag.id_], priority)

    def update_tag_priority(self, item, category, priority):
        tag = self._db.find_tag(item, category)
        tag_priority = tag[F.tag.priority]
        category_tags = self._db.get_tags({F.tag.cat: category[F.cat.name]}).sort(F.tag.priority)
        max_priority = category_tags.count()

        tag_reached = False
        for curr_tag in category_tags:
            if curr_tag is None:
                continue
            if tag == curr_tag:
                tag_reached = True
                self.edit_tag(tag, priority)
            elif tag_reached:
                curr_priority = curr_tag[F.tag.priority] + 1
                print(curr_tag)
                print(curr_priority)
                if curr_priority >= max_priority:
                    self.edit_tag(curr_tag, tag_priority)
                else:
                    self.edit_tag(curr_tag, curr_priority)
            continue

    def update_item_tags(self, item_name, new_cat_names: [str]):
        curr_cat_names = self._db.tags.find({F.tag.item: item_name}).distinct(F.tag.cat)

        for x in curr_cat_names:
            if x not in new_cat_names:
                self.remove_tag(item_name, x)

        for y in new_cat_names:
            if y not in curr_cat_names:
                self.add_tag(item_name, y)

    def remove_tag(self, item_name, cat_name):
        # options to remove all tags for a particular item or category, or remove a specific tag
        if item_name is None:
            self._db.tags.delete_many({F.tag.cat: cat_name})
        elif cat_name is None:
            self._db.tags.delete_many({F.tag.item: item_name})
        else:
            filters = {'$and': [{F.tag.item: item_name}, {F.tag.cat: cat_name}]}
            self._db.tags.delete_one(filters)

    # ========================================== Order Items =====================================

    def add_order_item(self, item_name, item_cost, quantity, table_no, iso_time, status):
        data = {
            F.ord_items.item: item_name,
            F.ord_items.cost: item_cost,
            F.ord_items.quantity: quantity,
            F.ord_items.table_no: table_no,
            F.ord_items.timestamp: iso_time,
            F.ord_items.status: status
        }
        order_id = self._db.ord_items.insert_one(data)
        return order_id

    def update_order_status(self, order_id, status):
        self._db.ord_items.update_one({F.ord_items.id_: order_id}, {'$set': {F.ord_items.status: status}})
