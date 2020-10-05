package com.example.waiterapp.Models;

import com.example.waiterapp.ApiRequests.ApiRequests;
import com.example.waiterapp.ApiRequests.AsyncTaskResult;
import com.example.waiterapp.ApiRequests.GetApiRequest;
import com.example.waiterapp.ApiRequests.PutApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Class to hold details of single Order Item from Customer Order.
 * It contains orderId, tableNo, itemName, quantity and
 * status fields.
 */
public class OrderItem {
    private String orderId;
    private String tableNo;
    private String itemName;
    private String quantity;
    private String status;

    private static HashMap<String, String> imgURLCache;

    /**
     * Constructor for OrderItem class.
     * @param orderId - Customer Order Number
     * @param tableNo - Customer Table Number
     * @param itemName - Menu Item Name
     * @param quantity - Menu Item Quantity
     * @param status - Status of the Order
     */
    public OrderItem(String orderId, String tableNo, String itemName, String quantity, String status) {
        this.orderId = orderId;
        this.tableNo = tableNo;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
    }

    // Getter and Setter Methods
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Method to To update Order Status to 'Done' and invoke REST API request
     *
     * @throws InterruptedException
     * @throws JSONException
     * @throws ExecutionException
     */
    public void incStatus() throws InterruptedException, JSONException, ExecutionException {
        String urlString = ApiRequests.domain + "/staff/orders";
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("order_id", this.getOrderId());
        jsonParam.put("status", "Done");
        String response = new PutApiRequest().execute(urlString, jsonParam.toString()).get();
    }

    /**
     * Get Image URL by item name
     * @return String: URL for item's image
     */
    public String getImageURL() throws Exception {
        if (imgURLCache == null) {
            imgURLCache = new HashMap<>();
        }

        String imageURL = imgURLCache.get(this.getItemName());
        if (imageURL != null){
            return imageURL;
        }

        String urlString = ApiRequests.domain + "/menu/" + this.getItemName() + "/image";
        AsyncTaskResult<String> result = new GetApiRequest().execute(urlString, "").get();

        if (result.getError() != null) { throw result.getError(); }
        String response = result.getResult();

        JSONObject jsonResponse = new JSONObject(response);
        imageURL = ApiRequests.domain + "/static/uploads/"+ jsonResponse.getString("image_url");

        imgURLCache.put(this.getItemName(), imageURL);
        return imageURL;
    }
}
