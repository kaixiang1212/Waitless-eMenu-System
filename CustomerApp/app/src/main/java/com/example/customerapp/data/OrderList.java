package com.example.customerapp.data;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

/**
 * This Object contains the list of Order Items
 */
public class OrderList {

    private int tableNo;
    @Expose(serialize = false)
    private Menu menu;
    private HashMap<Item, Integer> items;

    /**
     * Public constructor
     * @param tableNo : the table number
     * @param menu : the Menu object
     */
    public OrderList(int tableNo, Menu menu){
        this.tableNo = tableNo;
        this.menu = menu;
        this.items = new HashMap<>();
    }

    /**
     * Gets the items in the order
     * @return items
     */
    public HashMap<Item, Integer> getItems(){
        return this.items;
    }

    /**
     * adds an Item to the order
     * @param item : the item to add
     */
    public void addItem(Item item){
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    /**
     * Gets the table number
     * @return
     */
    public int getTableNo() {
        return tableNo;
    }
}
