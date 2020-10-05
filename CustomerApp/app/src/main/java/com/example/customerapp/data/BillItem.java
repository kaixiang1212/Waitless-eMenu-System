package com.example.customerapp.data;

/**
 * This Object represents a singular item in the Bill.
 */
public class BillItem {

    private String quantity;
    private String item;
    private String cost;

    /**
     * Public Constructor
     * @param qty : quantity of item
     * @param name : name of item
     * @param cost : cost of item
     */
    public BillItem(String qty, String name, String cost) {
        this.quantity = qty;
        this.item = name;
        this.cost = cost;
    }

    /**
     * Gets the quantity
     * @return quantity : quantity of the item
     */
    public String getQty() {
        return quantity;
    }

    /**
     * Gets the name
     * @return title : name of the item
     */
    public String getTitle() {
        return item;
    }

    /**
     * Gets the price of the item
     * @return cost : price of the item
     */
    public String getCost() {
        return cost;
    }

    /**
     * Gets the total price for the Item * quantity
     * @return Double.toString(unit * qty) : total price for the Item * quantity
     */
    public String getCostTotal() {
        double unit = Double.parseDouble(cost);
        int qty = Integer.parseInt(quantity);
        return  Double.toString(unit * qty);
    }
}
