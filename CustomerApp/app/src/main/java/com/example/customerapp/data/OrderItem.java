package com.example.customerapp.data;

/**
 * This Object represents a singular item in an Order.
 */
public class OrderItem{
    String item;
    String quantity;
    Float timestamp;

    /**
     * Gets the name of the item
     * @return item : the name of the Item
     */
    public String getItemName(){
        return item;
    }

    /**
     * Gets the quantity of item
     * @return quantity : the quantity of the item to order
     */
    public String getQuantity(){
        return quantity;
    }

    /**
     * Gets the timestamp
     * @return timestamp : the timestamp of order
     */
    public Float getTimestamp() {
        return timestamp;
    }
}
