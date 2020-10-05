import json
import time

from bson import ObjectId
from bson.json_util import dumps
from flask import request
from flask_restx import Resource
from server import mDB, api, MM, AQ
from src.database import F
from datetime import datetime

from src.helpers import serialise_obj


@api.route('/category')
class AllCategories(Resource):
    def get(self):
        """
        :return: All Json Formatted category item
        JSON formatted as follow:
        {
            "categories": [{
                "_id": "",
                "name": "",
                "priority": 1,
                "items": []
              },
              {
                "_id": "",
                "name": "",
                "priority": 2,
                "items": []
              }
            ]
        }
        """
        categories = []
        for category in mDB.get_categories().sort(F.cat.priority):
            category_name = category[F.cat.name]
            category_data = serialise_obj(category)
            category_data['items'] = [serialise_obj(item) for item in mDB.get_items_by_category_as_array(category_name)]
            categories.append(category_data)
        data = {'categories': categories}
        return data, 200


@api.route('/category/<string:cat_name>')
class SingleCategory(Resource):
    def get(self, cat_name):
        """
        :param: Category Name
        :return: Json Formatted category item
        JSON formatted as follow:
        {
            "category": {
                "_id": "5e72eb2d4765bf1ce65a0c1d",
                "name": "Sushi",
                "priority": 1
            },
            "items": [{ ITEM_JSON }]
        }
        """
        category = mDB.get_category({F.cat.name: cat_name})
        if category is None:
            return 404
        data = {'category': serialise_obj(category),
                'items': [serialise_obj(item) for item in mDB.get_items_by_category_as_array(cat_name)]}
        return data, 200


@api.route('/menu/<string:item_name>/image')
class ItemImage(Resource):
    def get(self, item_name):
        item = mDB.get_item_by_name(item_name)
        if item is None:
            return {}, 404
        return {'image_url': item[F.item.img_path]}


@api.route('/management/update_item_order')
class Item_Appearance_Order(Resource):
    """
    Accepted JSON Format: {
        category_name: `name`,
        data: `[ obj_id ]`
    }
    obj_id is sorted by priority, For example:
        [obj_id_1, obj_id_2]
        obj_id_1 has priority 1
        obj_id_2 has priority 2
    """

    def post(self):
        request_data = json.loads(request.data)
        category_name = request_data['category_name']
        obj_priority_array = request_data['data']
        category = mDB.get_category({F.cat.name: category_name})
        index = 1
        for obj_id in obj_priority_array:
            item = mDB.get_item({F.item.id_: ObjectId(obj_id)})
            MM.edit_tag_priority(item, category, index)
            index += 1

        return {"message": f"Menu items reordered successfully."}, 200

    def option(self):
        return {'Allow': 'POST'}, 200


@api.route('/management/update_category_order')
class Category_Appearance_Order(Resource):
    """
    Accepted JSON Format: {
        category_order: [ category_name ]
    }
    category_name is sorted by priority, For example:
        [ category_name_1, category_name_2 ]
        category_name_1 has priority 1
        category_name_2 has priority 2
    """

    def post(self):
        request_data = json.loads(request.data)
        category_array = request_data['category_order']

        index = 1
        for category_name in category_array:
            if not MM.edit_category_priority(category_name, index):
                return {"message": f"Internal Error"}, 500
            index += 1
        return {"message": f"Category reordered successfully"}, 200

    def option(self):
        return {'Allow': 'POST'}, 200


@api.route('/customer/order')
class Customer_Order(Resource):
    """
    Customer Submitting an Order
    Accepted JSON Format: {
        table_no:   int,
        items:      [
            {
                item:       'item_id',
                quantity:   int
            },
        ]
    }
    Return JSON Format: {
        order_ids:  [order_item_id,]
    }
    """

    def post(self):
        request_data = json.loads(request.data)

        timestring = time.time()

        table_no = request_data['table_no']

        order_list = []

        item_list = request_data['items']

        for item in item_list:
            item_id = item['item']
            quantity = item['quantity']

            # get item name and cost
            menu_item = mDB.get_item({F.item.id_: ObjectId(item_id)})
            name = menu_item[F.item.name]
            cost = menu_item[F.item.cost]

            # create ord_items entry in database
            order_id = MM.add_order_item(name, cost, quantity, table_no, timestring, "New")
            if order_id == None:
                return {"message": f"Internal Error"}, 500

            # add id to list
            order_list.append(str(order_id))

        # return list of ids
        return {"order_ids": order_list}, 200

    """
    Customer Checking Order Status
    Accepted JSON Format:   {
        table_no:  table_no
    }
    """

    def get(self):
        request_data = json.loads(request.data)

        order_id = request_data['order_id']

        order_item = mDB.get_order_item(ObjectId(order_id))

        status = order_item[F.ord_items.status]

        return {"order_status": status}, 200


@api.route('/customer/order/<string:table_no>')
class Customer_Table_Order(Resource):
    """
    Customer Checking Order Status
    Accepted JSON Format:   {
        table_no:  table_no
    }
    """
    def get(self, table_no):
        json_response = {}
        items = []
        order_item = mDB.get_table_order_item_by_status(table_no, 'New')
        for item in order_item:
            items.append(item)
        json_response['New'] = items

        items = []
        order_item = mDB.get_table_order_item_by_status(table_no, 'Preparing')
        for item in order_item:
            items.append(item)
        json_response['Preparing'] = items

        items = []
        order_item = mDB.get_table_order_item_by_status(table_no, 'Ready')
        for item in order_item:
            items.append(item)
        json_response['Ready'] = items

        items = []
        order_item = mDB.get_table_order_item_by_status(table_no, 'Done')
        for item in order_item:
            items.append(item)
        json_response['Done'] = items

        return json_response, 200



@api.route('/customer/bill')
class Customer_Bill(Resource):
    """
    Customer getting bill
    Accepted JSON Format:   {
        table_no:  table_no
    }
    """
    def post(self):
        request_data = json.loads(request.data)

        table_no = request_data['table_no']

        bill_items = mDB.get_order_items({F.ord_items.table_no: str(table_no)})

        if bill_items is None:
            return {}, 404

        data = {'bill_items': [serialise_obj(item) for item in bill_items]}
        return data, 200


@api.route('/staff/orders')
class Staff_Order(Resource):

    def get(self):
        """
        Staff getting all 'New' order items
        Return JSON Format:
        [
            {
                "order": {
                    "$oid": "5e78bda81d0a7454e376a49d"
                },
                "item": "Sushi",
                "quantity": 2
            },
            {
                "order": {
                    "$oid": "5e78bdf41d0a7454e376a49f"
                },
                "item": "Sushi",
                "quantity": 3
            }
        ]
        """
        order_items = mDB.get_items_by_order_status('New')
        items = []
        for item in order_items:
            items.append(serialise_obj(item))

        data = {"order_items": items}
        return data, 200

    def put(self):
        """
        Staff updating order status
        Accepted JSON Format:   {
            order_id:  order_id,
            status: status
        }
        returns {"message": "Order Status Updated"}, 200
        """
        request_data = json.loads(request.data)
        print(request_data)
        order_id = request_data['order_id']
        status = request_data['status']
        MM.update_order_status(ObjectId(order_id), status)
        return {"message": "Order Status Updated"}, 200


@api.route('/staff/orders/<string:status>')
class OrderStatus(Resource):
    def get(self, status):
        """
        Staff getting all order items with given status
        """
        order_items = mDB.get_items_by_order_status(status)
        items = []
        for item in order_items:
            items.append(serialise_obj(item))

        data = {"order_items": items}
        return data, 200


@api.route('/customer/assist')
class CustomerAssistance(Resource):
    def post(self):
        """
        Customer requesting assistance for their table
        Accepted JSON Format:   {
            table_no:   table_no
        }
        """
        request_data = json.loads(request.data)

        table_no = request_data['table_no']

        val = AQ.request_assistance(table_no)
        if val == False:
            return {"message": f"You have already requested assistance."}, 200
        else:
            return {"message": f"Requested assistance. A waiter will be with you shortly."}, 200


@api.route('/staff/assist')
class StaffAssistance(Resource):
    def get(self):
        """
        Waitstaff pulling pending assistance requests.
        """
        return dumps(AQ.queue()), 200

    def post(self):
        """
        Waitstaff makring an assistance request as resolved.
        Accepted JSON Format:   {
            table_no:   table_no
        }
        """
        request_data = json.loads(request.data)

        table_no = request_data['table_no']
        # FOR TESTING
        print(f"delete assist request {table_no}")

        val = AQ.resolve_assistance(table_no)
        if val == False:
            return {"message": f"No request for assistance from table {table_no}."}, 404
        else:
            return {"message": f"Assistance request resolved."}, 200
