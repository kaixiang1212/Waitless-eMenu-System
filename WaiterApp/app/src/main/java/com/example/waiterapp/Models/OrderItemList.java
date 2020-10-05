package com.example.waiterapp.Models;

import java.util.ArrayList;

/**
 * Class to hold a list of Order Items for a Customer Order.
 */
public class OrderItemList {
    public ArrayList<OrderItem> orderItemList;

    /**
     * Constructor method.
     */
    public OrderItemList() {
        orderItemList = new ArrayList<>();
    }

    /**
     * Add a new OrderItem to OrderItem List.
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }

    /**
     * Remove a specific OrderItem from OrderItem List.
     * @param orderItem
     */
    public void removeOrderItem(OrderItem orderItem) {
        orderItemList.remove(orderItem);
    }

    /**
     * Find an orderItem with specific orderId in
     * the orderList and return it.
     *
     * @param orderId
     * @return
     */
    public OrderItem findOrderItem(String orderId) {
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getOrderId().equals(orderId)) {
                return orderItem;
            }
        }
        return null;
    }

    /**
     * Update OrderItem List with a set of new OrderItems in
     * another OrderItem List.
     * @param other
     */
    public void updateOrderItemList(OrderItemList other) {
        for (OrderItem i : other.orderItemList) {
            if (this.findOrderItem(i.getOrderId()) == null) {
                this.addOrderItem(i);
            }
        }
    }

    /** Get Order Item List's first Order Item's status
     *
     * @return orderItem Status
     */
    public String getStatus(){
        if (orderItemList.size() > 0){
            return orderItemList.get(0).getStatus();
        }
        return "";
    }

    /**
     * Remove all items from Order Item List
     */
    public void removeAllOrderItems() {
        orderItemList.clear();
    }
}
