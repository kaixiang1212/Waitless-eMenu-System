package com.example.waiterapp.Models;

import java.util.ArrayList;

/**
 * POJO to hold a list of OrderItems parsed from OrderItems JSON returned by API
 */
public class OrderItemListJSON {
    ArrayList<OrderItemJSON> order_items = new ArrayList<>();

    public ArrayList<OrderItemJSON> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(ArrayList<OrderItemJSON> order_items) {
        this.order_items = order_items;
    }
}
