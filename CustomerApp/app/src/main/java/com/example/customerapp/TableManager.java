package com.example.customerapp;

import com.example.customerapp.Adapters.OrderListAdapter;
import com.example.customerapp.ApiRequests.AsyncTaskResult;
import com.example.customerapp.ApiRequests.GetApiRequest;
import com.example.customerapp.ApiRequests.PostApiRequest;
import com.example.customerapp.data.Bill;
import com.example.customerapp.data.BillItem;
import com.example.customerapp.data.Item;
import com.example.customerapp.data.Menu;
import com.example.customerapp.data.OrderList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The idea of this class is to encapsulate classes and function from ViewController
 */
public class TableManager {
    private OrderList orderList;
    static private Menu menu;
    private String domain = "http://10.0.2.2:8080";
    private static int tableNo;

    /**
     * Constructor for TableManager Class
     * @param tableNo : the table number assigned to the app
     */
    private TableManager(int tableNo) {
        this.menu = new Menu();
        fetchMenu(4);
        this.orderList = new OrderList(tableNo, menu);
        this.tableNo = tableNo;
    }

    /**
     * Setter method to enable setting of the table number
     * @param tableInput : user-inputted table number.
     */
    public static void setTableNo(int tableInput) {
        tableNo = tableInput;
    }

    /**
     * Getter method for getting of the table number
     * @return tableNo : assigned table number
     */
    public Integer getTableNo() { return tableNo; }

    /**
     * Builds and sends HTTP Get request to fetch the menu from server API endpoint
     * @param triesLeft : indicates the total number of calls made to server to limit endless waiting if server is unresponsive
     */
    private void fetchMenu(int triesLeft) {
        String urlString = domain + "/category";
        AsyncTaskResult<String> result = null;
        String jsonString = "";
        try {
            result = new GetApiRequest().execute(urlString, "").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (result != null && result.getError() == null) {
            jsonString = result.getResult();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        menu = gson.fromJson(jsonString, Menu.class);
        System.out.println(menu);
        if (menu == null && triesLeft > 0) {
            System.out.println("fetch menu failed: x" + (5 - triesLeft));
            fetchMenu(triesLeft - 1);
        }
    }

    /**
     * Getter method for getting the menu object
     * @return menu : the menu object.
     */
    public Menu getMenu() {
        return this.menu;
    }

    /**
     * Given an itemId, add the item to the order list
     * @param itemId the id of the item
     */
    public void addItemToOrderList(String itemId){
        Item item = menu.getItem(itemId);
        orderList.addItem(item);
    }

    /**
     * Sends a HTTP POST request to the server with the current order list
     */
    public void sendOrder(){
        final String urlString = domain + "/api/testing";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OrderList.class, new OrderListAdapter())
                .create();
        String jsonString = gson.toJson(orderList);
        String response = "";
        try {
            response = new PostApiRequest().execute(urlString, jsonString).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }

    /**
     * Sends a HTTP POST request to the server to request assistance
     */
    public String requestAssistance() {
        final String urlString = domain + "/customer/assist";
        JSONObject json = new JSONObject();
        JSONObject response;
        String stringResponse;

        try {
            json.put("table_no", this.tableNo);
            String jsonString = json.toString();
            stringResponse = new PostApiRequest().execute(urlString, jsonString).get();
            response = new JSONObject(stringResponse);
            return response.getString("message");
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return "An error occurred";
    }

    private static TableManager tableManager;

    /**
     * Quickly creates and returns a new instance of TableManager
     * @param tableNo : table number to be assigned
     * @return tableManager : newly created TableManager instance
     */
    public static TableManager getInstance(int tableNo){
        if (tableManager == null){
            tableManager = new TableManager(tableNo);
        }
        return tableManager;
    }
    /**
     * Quickly creates and returns a new instance of TableManager
     * @return tableManager : newly created TableManager instance
     */
    public static TableManager getInstance(){
        if (tableManager == null){
            tableManager = new TableManager(tableNo);
        }
        return tableManager;
    }

    /**
     * Sends a HTTP GET request to the server to request the bill
     * @param triesLeft : indicates the total number of calls made to server to limit endless waiting if server is unresponsive
     * @return tempBill.getItems : a list of BillItem objects to be used to display bill and calculate total amount payable
     */
    public List<BillItem> getBill(int triesLeft) {
        String urlString = domain + "/customer/bill";
        JSONObject json = new JSONObject();
        String stringResponse = "";
        try {
            json.put("table_no", this.tableNo);
            String jsonString = json.toString();
            stringResponse = new PostApiRequest().execute(urlString, jsonString).get();
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Bill tempbill = gson.fromJson(stringResponse, Bill.class);
        if (tempbill == null && triesLeft > 0) {
            System.out.println("fetch bill failed: x" + (5 - triesLeft));
            return getBill(triesLeft - 1);
        } else if (tempbill == null) {
            return Collections.emptyList();
        }
        return tempbill.getItems();
    }
}