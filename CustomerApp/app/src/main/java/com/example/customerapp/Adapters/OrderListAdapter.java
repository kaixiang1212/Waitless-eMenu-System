package com.example.customerapp.Adapters;

import com.example.customerapp.data.Item;
import com.example.customerapp.data.OrderList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This object Adapts the OrderList layout and views
 */
public class OrderListAdapter implements JsonSerializer<OrderList> {
    /**
     * Serialised all data into Json format for use with API calls.
     * @param src : order list to source data from
     * @param typeOfSrc : type of source identifier
     * @param context : Android system context object
     * @return obj : the json formatted version of the orderlist
     */
    @Override
    public JsonElement serialize(OrderList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("table_no", src.getTableNo());
        JsonArray items = new JsonArray();
        Iterator it = src.getItems().entrySet().iterator();
        while (it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Item item = (Item) pair.getKey();
            int quantity = (int) pair.getValue();
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", item.getId());
            itemJson.addProperty("quantity", quantity);
            items.add(itemJson);
        }
        obj.add("items", items);
        return obj;
    }
}
