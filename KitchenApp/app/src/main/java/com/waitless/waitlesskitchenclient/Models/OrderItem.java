package com.waitless.waitlesskitchenclient.Models;

import com.waitless.waitlesskitchenclient.ApiRequests.ApiRequests;
import com.waitless.waitlesskitchenclient.ApiRequests.AsyncTaskResult;
import com.waitless.waitlesskitchenclient.ApiRequests.GetApiRequest;
import com.waitless.waitlesskitchenclient.ApiRequests.PutApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class OrderItem {
    private String _id;
    private String item;
    private Integer quantity;
    private String table_no;
    private String status;
    private Double timestamp;

    /** Cache system so that same url doesn't need to be fetched twice */
    private static HashMap<String, String> imgURLCache;

    public OrderItem(String itemName, int quantity, String table, String id) {
        this.item = itemName;
        this.quantity = quantity;
        table_no = table;
        _id = id;
    }

    public Double getTimeCreated(){
        return timestamp;
    }

    /**
     * @return quantity of the item order
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @return item name of the item order
     */
    public String getItemName() {
        return item;
    }

    /**
     * @return table number of the item order
     */
    public String getTable_no() {
        return table_no;
    }

    /**
     * @return item status
     */
    String getStatus(){
        return status;
    }

    /**
     * Increments item status, sends a PUT request to server to update status
     */
    public void incStatus() throws InterruptedException, JSONException, ExecutionException {
        switch (status){
            case "New":
                status = "Preparing";
                break;
            case "Preparing":
                status = "Ready";
                break;
            case "Ready":
                status = "Done";
                break;
        }
        String urlString = ApiRequests.domain + "/staff/orders";
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("order_id", this._id);
        jsonParam.put("status", this.status);
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

        String imageURL = imgURLCache.get(item);
        if (imageURL != null){
            return imageURL;
        }

        String urlString = ApiRequests.domain + "/menu/" + item + "/image";
        AsyncTaskResult<String> result =  new GetApiRequest().execute(urlString, "").get();

        if (result.getError() != null) { throw result.getError(); }
        String response = result.getResult();

        JSONObject jsonResponse = new JSONObject(response);
        imageURL = ApiRequests.domain + "/static/uploads/"+ jsonResponse.getString("image_url");

        imgURLCache.put(item, imageURL);
        return imageURL;
    }
}