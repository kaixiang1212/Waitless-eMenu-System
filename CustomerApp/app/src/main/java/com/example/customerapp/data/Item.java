package com.example.customerapp.data;

import java.util.List;

/**
 * This Object represent a singular food item in the menu
 */
public class Item {

    private String name;
    private String description;
    private double cost;
    private List<String> categories;
    private String imagePath;
    private List<String> ingredients;
    private String _id;

    public Item(){
        //Required empty public constructor
    }

    /**
     * Public Constructor
     * @param _id : ID of Item
     * @param title : name of Item
     * @param description : description of Item
     * @param price : price of Item
     * @param categories : categories that the Item belongs to
     * @param imagePath : the pathname of the image of Item
     */
    public Item(String _id, String title, String description, double price, List<String> categories, String imagePath){
        this.name = title;
        this.description = description;
        this.cost = price;
        this.categories = categories;
        this.imagePath = imagePath;
    }

    /**
     * Public Constructor
     * @param _id : ID of Item
     * @param title : name of Item
     * @param price : price of Item
     * @param ingredients : ingredients of Item
     * @param description : description of Item
     */
    public Item(String _id, String title, double price, List<String> ingredients, String description){
        this._id =_id;
        this.name = title;
        this.cost = price;
        this.ingredients = ingredients;
        this.description = description;
    }

    /**
     * Gets the name of the item
     * @return name : name of item
     */
    public String getTitle() {
        return name;
    }

    /**
     * Sets the name of the item
     * @param title : new name of item
     */
    public void setTitle(String title) {
        this.name = title;
    }

    /**
     * Gets the description of the Item
     * @return description : description of item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Item
     * @@param description : description of item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price of the Item
     * @return cost : price of item
     */
    public double getPrice() {
        return this.cost;
    }

    /**
     * Sets the price of the Item
     * @param price : the new price of the item
     */
    public void setPrice(double price) {
        this.cost = price;
    }

    /**
     * Gets the categories that the Item belongs to
     * @return categories : categories item belongs to
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Gets the pathname for the Image of the Item
     * @return imagepath : pathname for the Image of the Item
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the ID for the Item
     * @return _id : id of the item
     */
    public String getId() {
        return _id;
    }
}
