package com.example.customerapp.OrderCart;

import com.example.customerapp.data.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is to hold and keep track of the different items in the order cart
 */
public class CartList {
    public ArrayList<CartItem> cart_list;

    /**
     * This Object acts as the cart and holds CartItem objects that are present in the cart of the user
     */
    public CartList() {
        cart_list = new ArrayList<>();
    }

    /***
     * Adding menu items to our order cart.
     * @param item - a MENU ITEM that is to be added to the cart
     */
    public void addToCartList(Item item) {
        // if the item is already in cart, increase quantity
        for (CartItem ci : cart_list) {
            if (ci.getItem().getId().equals(item.getId())) {
                ci.incQuantity();
                return;
            }
        }
        // if the item is not already in cart, add to cart
        cart_list.add(new CartItem(item));
    }

    /***
     * Removing a CartItem from the order cart. No matter the quantity
     * of the item, all will be removed from the cart.
     * @param cItem - the cart item to be removed
     */
    public void removeFromCartList(CartItem cItem) {
        cart_list.remove(cItem);
    }

    /**
     * Checks if cart is empty
     * @return : true if empty, false if !empty
     */
    public Boolean isEmpty() {
        return cart_list.isEmpty();
    }

    /**
     * Returns the cart total payable
     * @return cartTotal : total amount payable for the items in cart
     */
    public double cartTotal() {
        double total = 0.0;
        for (CartItem ci : cart_list) {
            total += ci.getQuantity() * ci.getItem().getPrice();
            System.out.println(String.format("%d X %s",ci.getQuantity(), ci.getItem().getTitle()));
        }
        return total;
    }

    /**
     * Produces a JSONArray representing the Cart List.
     * Should be used to produce the payload of an API call to submit this order.
     * @return JSONArray of items and quantities
     * @throws JSONException
     */
    public JSONArray itemAndQuantity() throws JSONException {
        JSONArray items = new JSONArray();
        for (CartItem ci: cart_list) {
            JSONObject obj = new JSONObject();
            obj.put("item", ci.getItem().getId());
            obj.put("quantity", ci.getQuantity());
            items.put(obj);
        }
        return items;
    }

    /**
     * Empties the CartList.
     * Should be used after the order is successfully submitted to the server.
     */
    public void emptyCartList() {
        cart_list.clear();
    }
}
