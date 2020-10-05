package com.waitless.waitlesskitchenclient.Models;

import java.util.ArrayList;

public class OrderList {
    public ArrayList<OrderItem> order_items;

    public OrderList(){
        order_items = new ArrayList<>();
    }

    /**
     * Returns order status of the list
     * @return order status of the list
     */
    public String getStatus(){
        if (order_items.size() > 0){
            return order_items.get(0).getStatus();
        }
        return "";
    }

    /**
     * Add Order Item to the list
     * @param orderItem order item to be added
     */
    public void addOrderItem(OrderItem orderItem){
        order_items.add(orderItem);
    }

    /**
     * Remove Order Item from the list
     * @param orderItem order item to be removed
     */
    public void removeOrderItem(OrderItem orderItem){
        order_items.remove(orderItem);
    }

}

