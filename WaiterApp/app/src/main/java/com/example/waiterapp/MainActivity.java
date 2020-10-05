package com.example.waiterapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.Adapters.AssistListAdapter;
import com.example.waiterapp.Adapters.OrderItemListAdapter;
import com.example.waiterapp.ApiRequests.ApiRequests;
import com.example.waiterapp.ApiRequests.AsyncTaskResult;
import com.example.waiterapp.ApiRequests.DeleteAssistanceRequest;
import com.example.waiterapp.ApiRequests.GetApiRequest;
import com.example.waiterapp.ApiRequests.GetAssistanceRequests;
import com.example.waiterapp.ApiRequests.PostApiRequest;
import com.example.waiterapp.Models.AssistList;
import com.example.waiterapp.Models.AssistListItem;
import com.example.waiterapp.Models.OrderItem;
import com.example.waiterapp.Models.OrderItemJSON;
import com.example.waiterapp.Models.OrderItemList;
import com.example.waiterapp.Models.OrderItemListJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Main Class which holds together all the different pieces of this
 * Android application.
 *
 */
public class MainActivity extends AppCompatActivity {

    public static AssistListAdapter assistAdapter;
    public AssistList assistList = new AssistList();
    public OrderItemList readyToDeliverItemList = new OrderItemList();
    public static OrderItemListAdapter readyToDeliverItemListAdapter;
    public OrderItemList pendingOrderItemList = new OrderItemList();
    public static OrderItemListAdapter pendingOrderItemListAdapter;

    static ScheduledThreadPoolExecutor executor;

    /**
     * Overridden method which is called when the Android App
     * is first loaded.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleRefreshTask();

        // recycler views and adapters
        // ASSIST LIST
        RecyclerView assistRView = (RecyclerView) findViewById(R.id.assist_view);
        assistAdapter = new AssistListAdapter(this.getLayoutInflater(), assistList);
        assistRView.setLayoutManager(new LinearLayoutManager(this));
        assistRView.setHasFixedSize(true);
        assistRView.setAdapter(assistAdapter);

        // ORDER LIST
        RecyclerView readyForDeliveryOrdersRecyclerView = (RecyclerView) findViewById(R.id.readyForDeliveryOrdersRecyclerView);
        readyToDeliverItemListAdapter = new OrderItemListAdapter(readyToDeliverItemList, this.getLayoutInflater());
        readyForDeliveryOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        readyForDeliveryOrdersRecyclerView.setHasFixedSize(true);
        readyForDeliveryOrdersRecyclerView.setAdapter(readyToDeliverItemListAdapter);

        RecyclerView pendingOrdersRecyclerView = (RecyclerView) findViewById(R.id.pendingOrdersRecyclerView);
        pendingOrderItemListAdapter = new OrderItemListAdapter(pendingOrderItemList, this.getLayoutInflater());
        pendingOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pendingOrdersRecyclerView.setHasFixedSize(true);
        pendingOrdersRecyclerView.setAdapter(pendingOrderItemListAdapter);
    }

    /**
     * Method to update customer assistance requests every three seconds
     */
    Runnable updateAssists = new Runnable() {
        @Override
        public void run() {
            try {
                String urlString = ApiRequests.domain + "/staff/assist";
                AssistList _newList = new GetAssistanceRequests().execute(urlString).get();
                // update list and view
                assistList.updateAssistList(_newList);
                runOnUiThread(updateAssistUI);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Method to update the OrderItemsList with new items every
     * three seconds.
     */
    Runnable updateOrderItemList = new Runnable() {
        @Override
        public void run() {
            try {
                OrderItemList newReadyToDeliverItemList = convertOrderItemListJSONToOrderItemList(fetchOrderItemListJSON("Ready"));
                // update list and view
                readyToDeliverItemList.updateOrderItemList(newReadyToDeliverItemList);

                OrderItemList newPendingOrderItemList = new OrderItemList();
                OrderItemListJSON newOrderItemListJSON = fetchOrderItemListJSON("New");
                OrderItemListJSON preparingOrderItemListJSON = fetchOrderItemListJSON("Preparing");

                for (OrderItemJSON orderItemJSON : newOrderItemListJSON.getOrder_items()) {
                    newPendingOrderItemList.addOrderItem(new OrderItem(orderItemJSON.get_id(), orderItemJSON.getTable_no(), orderItemJSON.getItem(), orderItemJSON.getQuantity(), orderItemJSON.getStatus()));
                }
                for (OrderItemJSON orderItemJSON : preparingOrderItemListJSON.getOrder_items()) {
                    newPendingOrderItemList.addOrderItem(new OrderItem(orderItemJSON.get_id(), orderItemJSON.getTable_no(), orderItemJSON.getItem(), orderItemJSON.getQuantity(), orderItemJSON.getStatus()));
                }

                pendingOrderItemList.removeAllOrderItems();
                pendingOrderItemList.updateOrderItemList(newPendingOrderItemList);

                runOnUiThread(updateReadyToDeliverItemListUI);

            } catch (Exception e) {
                scheduleRefreshTask();
                e.printStackTrace();
            }
        }
    };

    /**
     * Method to update App UI with updated list.
     */
    public static Runnable updateAssistUI = new Runnable() {
        @Override
        public void run() {
            assistAdapter.notifyDataSetChanged();
        }
    };

    /**
     * Method to update App UI with updated list.
     */
    public static Runnable updateReadyToDeliverItemListUI = new Runnable() {
        @Override
        public void run() {
            readyToDeliverItemListAdapter.notifyDataSetChanged();
            pendingOrderItemListAdapter.notifyDataSetChanged();
        }
    };

    /**
     * Method to resolve a Customer Assistance request.
     *
     * @param v
     */
    public void resolveAssist (View v) {
        // NICE TO HAVE?
        // confirmation check

        // get table number
        Integer t_no = (Integer) v.getTag();
        String table_no = t_no.toString();
        // make API post
        try {
            String urlString = ApiRequests.domain + "/staff/assist";
            new DeleteAssistanceRequest().execute(urlString, table_no).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // delete from list
        AssistListItem i = assistList.findAssistListItem(table_no);
        if (i == null) {
            return;
        }
        assistList.removeAssistListItem(i);
        // refresh list
        assistAdapter.notifyDataSetChanged();
    }

    public void deliverOrder (View v) throws JSONException {
        // get table number
        String orderId = (String) v.getTag();

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("order_id", orderId);
        jsonParam.put("status", "Done");

        // make API post
        try {
            String urlString = ApiRequests.domain + "/staff/orders";
            String response = new PostApiRequest().execute(urlString, jsonParam.toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // delete from list
        OrderItem i = readyToDeliverItemList.findOrderItem(orderId);
        if (i == null) {
            return;
        }
        readyToDeliverItemList.removeOrderItem(i);
        // refresh list
        readyToDeliverItemListAdapter.notifyDataSetChanged();
    }

    /**
     * Method to fetch orders via REST API Call based on input status
     * and parse the JSON response into Java Object.
     *
     * @param status
     * @return orderItemListJSON
     * @throws Exception
     */
    public OrderItemListJSON fetchOrderItemListJSON(String status) throws Exception {
        String jsonString = "";
        String urlString = ApiRequests.domain + "/staff/orders/" + status;
        AsyncTaskResult<String> result = new GetApiRequest().execute(urlString, "").get();

        if (result.getError() != null){
            throw result.getError();
        }
        jsonString = result.getResult();
        /* Parse JSON to Objects in Java */
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, OrderItemListJSON.class);
    }

    /**
     * Convert orderItemListJSON to orderItemList objects
     * 
     * @param orderItemListJSON
     * @return
     */
    public OrderItemList convertOrderItemListJSONToOrderItemList(OrderItemListJSON orderItemListJSON) {
        OrderItemList orderItemList = new OrderItemList();
        for (OrderItemJSON orderItemJSON : orderItemListJSON.getOrder_items()) {
            orderItemList.addOrderItem(new OrderItem(orderItemJSON.get_id(), orderItemJSON.getTable_no(), orderItemJSON.getItem(), orderItemJSON.getQuantity(), orderItemJSON.getStatus()));
        }
        return orderItemList;
    }

    /**
     * Schedule order list to run every 3 seconds
     */
    private void scheduleRefreshTask() {
        if (executor != null) { executor.shutdownNow(); }
        executor = new ScheduledThreadPoolExecutor(1);
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        executor.scheduleAtFixedRate(updateOrderItemList, 3, 3, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(updateAssists, 3, 3, TimeUnit.SECONDS);
    }
}
