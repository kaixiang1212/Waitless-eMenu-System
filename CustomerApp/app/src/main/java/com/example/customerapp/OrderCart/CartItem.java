package com.example.customerapp.OrderCart;

import com.example.customerapp.data.Item;

/**
 * This object is to represent items in the CartList.
 * Contains the Item and its Quantity.
 */
public class CartItem {
    private Item item;
    private Integer quantity;

    /**
     * Constructor for CartItem.
     * @param i : the Menu Item
     */
    public CartItem(Item i) {
        item = i;
        quantity = 1;
    }

    /**
     * Increases the quantity of this item by 1
     */
    public void incQuantity() {
        quantity += 1;
    }

    /**
     * Decreases the quantity of this item by 1
     */
    public void decQuantity() {
        quantity -= 1;
    }

    /**
     * Returns the Item object this CartItem holds
     * @return Item : the Item object
     */
    public Item getItem() {
        return item;
    }

    /**
     * Returns the quantity of the item in this CartItem
     * @return Quantity : quantity of the item
     */
    public Integer getQuantity() {
        return quantity;
    }
}
