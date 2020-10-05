package com.example.customerapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This Object represent a singular Category in the menu
 */
public class Category {
    private List<Item> items;
    private String name;
    private int priority;
    private String _id;

    /**
     * Public Constructor
     * @param items : Items in category
     * @param title : Name of Category
     */
    public Category(List<Item> items, String title) {
        this.items = items;
        this.name = title;
    }

    /**
     * Public Constructor
     * @param title : Name of Category
     */
    public Category(String title) {
        this.name = title;
        this.items = new ArrayList<Item>();
    }

    /**
     * Gets the title
     * @return
     */
    public String getTitle() {
        return name;
    }

    /**
     * Gets the Items in the category
     * @return items : items in category
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Gets the size of the Category ie. how many items in category
     * @return
     */
    public int size() {
        return items.size();
    }

    /**
     * Gets the Item corresponding to the ItemID
     * @param itemId : the ID of the item to get
     * @return item : the item corresponding to the ID
     */
    public Item getItem(String itemId){
        for (Item item: items){
            if (item.getId().equals(itemId)){
                return item;
            }
        }
        return null;
    }

    /**
     * Gets the Item corresponding to the ItemID
     * @param itemName : the name of the item to get
     * @return item : the item corresponding to the ID
     */
    public Item getItemByName(String itemName){
        for (Item item: items){
            if (item.getTitle().equals(itemName)){
                return item;
            }
        }
        return null;
    }
}

