package com.waitless.waitlesskitchenclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.waitless.waitlesskitchenclient.Adapters.ItemsAdapter;
import com.waitless.waitlesskitchenclient.ApiRequests.ApiRequests;
import com.waitless.waitlesskitchenclient.ApiRequests.AsyncTaskResult;
import com.waitless.waitlesskitchenclient.ApiRequests.GetApiRequest;
import com.waitless.waitlesskitchenclient.Models.OrderList;

public class MainActivity extends AppCompatActivity {

    OrderList pendingOrderList = new OrderList();
    OrderList preparingOrderList = new OrderList();
    OrderList readyOrderList = new OrderList();
    static String sortBy = "Time";
    static Boolean groupOrder = false;
    static Boolean groupTable = false;

    public static ItemsAdapter pendingItemsAdapter;
    public static ItemsAdapter preparingItemsAdapter;
    public static ItemsAdapter readyItemsAdapter;
    public static ArrayList<ItemsAdapter> adapters;

    static ScheduledThreadPoolExecutor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleRefreshTask();

        RadioGroup radioGroup = findViewById(R.id.groupRadioGroup);
        radioGroup.setVisibility(View.VISIBLE);

        RadioButton sortTime = findViewById(R.id.sortTime);
        sortTime.setOnClickListener(v -> {
            runOnUiThread( ()-> radioGroup.setVisibility(View.VISIBLE));
            sortBy = "Time";
            updateRecycleViews.run();
        });


        RadioButton sortTable = findViewById(R.id.sortTable);
        sortTable.setOnClickListener(v -> {
            runOnUiThread( ()-> radioGroup.setVisibility(View.INVISIBLE));
            sortBy = "Table";
            updateRecycleViews.run();
        });
        /* Initialise Recycle Views and Adapters */
        initAdapters();

        RadioButton groupNoneRadio = findViewById(R.id.noneRadioButton);
        RadioButton groupOrderRadio = findViewById(R.id.groupItemCheckBox);
        RadioButton groupTableRadio = findViewById(R.id.groupTableCheckBox);

        groupNoneRadio.setOnClickListener(v -> {
            groupOrder = false;
            groupTable = false;
            updateRecycleViews.run();
        });
        groupOrderRadio.setOnClickListener(v -> {
            groupTable = false;
            groupOrder = true;
            updateRecycleViews.run();
        });
        groupTableRadio.setOnClickListener(v -> {
            groupTable = true;
            groupOrder = false;
            updateRecycleViews.run();
        });
    }

    /**
     * Initialise Adapters for every RecyclerView
     */
    private void initAdapters(){
        RecyclerView readyRView = findViewById(R.id.readyRView);
        readyItemsAdapter = new ItemsAdapter(getLayoutInflater(), readyOrderList);
        readyRView.setAdapter(readyItemsAdapter);
        readyRView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        RecyclerView preparingRView = findViewById(R.id.preparingRView);
        preparingItemsAdapter = new ItemsAdapter(getLayoutInflater(), preparingOrderList);
        preparingRView.setAdapter(preparingItemsAdapter);
        preparingRView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        RecyclerView pendingRView = findViewById(R.id.pendingRView);
        pendingItemsAdapter = new ItemsAdapter(getLayoutInflater(), pendingOrderList);
        pendingRView.setAdapter(pendingItemsAdapter);
        pendingRView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        adapters = new ArrayList<>();
        adapters.add(readyItemsAdapter);
        adapters.add(preparingItemsAdapter);
        adapters.add(pendingItemsAdapter);
    }

    /**
     * Fetch order list from server with given status
     * @param status    Status of the order list : New, Preparing, Ready
     * @return          New order list fetched from server
     */
    public OrderList fetchOrderList(String status) throws Exception {
        String jsonString = "";
        String urlString = ApiRequests.domain + "/staff/orders/" + status;
        AsyncTaskResult<String> result = new GetApiRequest().execute(urlString, "").get();

        if (result.getError() != null){
            throw result.getError();
        }
        jsonString = result.getResult();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, OrderList.class);
    }

    /**
     * Update and sort Recycler Views
     */
    public static Runnable updateRecycleViews = new Runnable() {
        @Override
        public void run() {
            if (adapters != null) {
                for (ItemsAdapter adapter: adapters){
                    if (sortBy.equals("Time")){
                        adapter.sortByTime();
                    } else {
                        adapter.sortByTable();
                    }
                    if (groupOrder){
                        adapter.groupByItem();
                    } else if (groupTable){
                        adapter.groupByTable();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    /**
     * Schedule order list to run every 3 seconds
     */
    private void scheduleRefreshTask(){
        if (executor != null){ executor.shutdownNow(); }
        executor = new ScheduledThreadPoolExecutor(1);
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        executor.scheduleAtFixedRate(updateOrderLists, 3, 3, TimeUnit.SECONDS);
    }

    /**
     * Update order lists fetched from server
     */
    Runnable updateOrderLists = () -> {
        try {
            pendingOrderList.order_items =  fetchOrderList("New").order_items;
            preparingOrderList.order_items = fetchOrderList("Preparing").order_items;
            readyOrderList.order_items = fetchOrderList("Ready").order_items;
            /* Update UI */
            runOnUiThread(updateRecycleViews);
        } catch (Exception e) {
            // TODO: Show error
            scheduleRefreshTask();
            e.printStackTrace();
        }
    };

}
