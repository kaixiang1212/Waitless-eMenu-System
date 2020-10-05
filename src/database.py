from pymongo import MongoClient


class F:
    """
    Field Constant for database variable
    """

    class item:
        id_ = "_id"
        desc = "description"
        name = "name"
        cost = "cost"
        ingredients = "ingredients"
        img_path = "imagePath"

    class cat:
        id_ = "_id"
        name = "name"
        priority = 'priority'

    class tag:
        id_ = "_id"
        item = "item"
        cat = "category"
        priority = 'priority'

    class ord_items:
        id_ = "_id"
        item = "item"
        cost = "cost"
        quantity = 'quantity'
        table_no = 'table_no'
        timestamp = "timestamp"
        status = "status"


class MenuDB:

    def __init__(self):
        self._client = MongoClient()
        self.db = self._client.menu_database

        # THE FOLLOWING ARE OUR DB TABLES/COLLECTIONS
        # categories:   				{"name": [string: category name],
        #                   			"order": [int: appearance order of category]}
        self.cats = self.db.categories

        # items:        				{"name": [string: item name],
        #                   			""}
        self.items = self.db.items
        # item-category relationship:	{}
        self.tags = self.db.item_categories
        # orders:						{}
        self.orders = self.db.orders
        # order-item relationship:		{}
        self.ord_items = self.db.order_items

    def drop_db(self):
        self._client.drop_database("menu_database")

    # Database queries
    # 	assume all input is valid etc
    #	keep business logic in api.py

    # //// CATEGORIES /////////////////////////////////////////////////////////

    # find one particular category
    def get_category(self, filters):
        return self.cats.find_one(filters)

    # return all categories
    def get_categories(self, filters={}, sort=False):
        result = self.cats.find(filters)
        if sort == True:
            result = result.sort(F.cat.priority)
        return result

    # //// ITEMS //////////////////////////////////////////////////////////////

    # find one particular item
    def get_item(self, filters):
        return self.items.find_one(filters)

    # return all items
    def get_items(self, filters={}):
        return self.items.find(filters)

    # find one particular item by name
    def get_item_by_name(self, item_name):
        return self.get_item({F.item.name: item_name})

    # //// ITEM-CATEGORIES / TAGS /////////////////////////////////////////////

    # find one particular item-category link
    def get_tag(self, filters):
        return self.tags.find_one(filters)

    # return all item-category links
    def get_tags(self, filters={}):
        return self.tags.find(filters)

    # get the item-category link given an item and a category
    def find_tag(self, item, category):
        query = {'$and': [
            {F.tag.item: item[F.item.name]},
            {F.tag.cat: category[F.cat.name]}
        ]}
        return self.get_tag(query)

    def get_item_tag_list_str(self, item_name):
        return self.tags.find({F.tag.item: item_name}).distinct(F.tag.cat)

    def get_all_items_grouped_by_category(self):
        categories = self.cats.find().sort(F.cat.priority)
        categories_item = {}
        for x in categories:
            category_name = x[F.cat.name]
            category_items = self.get_items_by_category_as_array(category_name)
            categories_item[category_name] = category_items

        return categories_item

    def get_items_by_category_as_array(self, cat_name):
        items = []
        tags = self.tags.find({F.tag.cat: cat_name}).sort(F.tag.priority)
        for x in tags:
            item_name = x[F.tag.item]
            item = self.get_item({F.item.name: item_name})
            if item is not None:
                items.append(item)
        return items

    # //// ORDERS /////////////////////////////////////////////////////////////

    def get_order_item(self, order_id):
        return self.ord_items.find({F.ord_items.id_: order_id})

    def get_table_order_item_by_status(self, table_no, status):
        query = {'$and': [
            {F.ord_items.table_no: table_no},
            {F.ord_items.status: status}
        ]}
        return self.ord_items.find(query, {F.ord_items.id_: 0, F.ord_items.status: 0, F.ord_items.cost: 0,
                                           F.ord_items.table_no: 0})

    def get_order_items(self, filters={}):
        return self.ord_items.find(filters)

    # //// KITCHEN STAFF //////////////////////////////////////////////////////
    def get_items_by_order_status(self, status):
        return self.ord_items.find({F.ord_items.status: status},
                                   {F.ord_items.table_no: 1, F.ord_items.item: 1, F.ord_items.quantity: 1,
                                    F.ord_items.status: 1, F.ord_items.timestamp: 1, F.ord_items.id_: 1})

    def get_items_by_table_no(self, table_no):
        return self.ord_items.find({F.ord_items.table_no: table_no},
                                   {F.ord_items.table_no: 1, F.ord_items.item: 1, F.ord_items.quantity: 1,
                                    F.ord_items.id_: 0})
