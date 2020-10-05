package com.example.customerapp.data;

import android.graphics.Bitmap;

import com.example.customerapp.data.Category;
import com.example.customerapp.data.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * This Object is the menu and contains categories in the menu.
 */
public class Menu {

    private List<Category> categories;

    /**
     * Public constructor
     */
    public Menu(){
        categories = new ArrayList<Category>();
    }

    /**
     * Gets the categories in menu
     * @return categories : the categories in the menu
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Gets Item by itemId
     * @param itemId : the id of the Item
     * @return item : the item of the corresponding ID
     */
    public Item getItem(String itemId){
        Item item;
        for (Category category:categories){
            if ((item = category.getItem(itemId)) != null){
                return item;
            }
        }
        return null;
    }

    /**
     * Gets Item by name
     * @param itemName : the name of the Item
     * @return : the item of the corresponding name
     */
    public Item getItemByName(String itemName){
        Item item;
        for (Category category: categories){
            if ((item = category.getItemByName(itemName)) != null){
                return item;
            }
        }
        return null;
    }
}
