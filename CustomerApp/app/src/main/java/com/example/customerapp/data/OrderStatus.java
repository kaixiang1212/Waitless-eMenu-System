package com.example.customerapp.data;

import java.util.ArrayList;

/**
 * This Object Contains the different items in the different stages of the process categorised by status
 */
public class OrderStatus {
    public ArrayList<OrderItem> New;
    public ArrayList<OrderItem> Preparing;
    public ArrayList<OrderItem> Ready;
    public ArrayList<OrderItem> Done;

    /**
     * Public constructor
     */
    public OrderStatus(){
        New = new ArrayList<>();
        Preparing = new ArrayList<>();
        Ready = new ArrayList<>();
        Done = new ArrayList<>();
    }
}

