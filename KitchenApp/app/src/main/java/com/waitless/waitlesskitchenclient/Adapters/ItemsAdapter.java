package com.waitless.waitlesskitchenclient.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waitless.waitlesskitchenclient.MainActivity;
import com.waitless.waitlesskitchenclient.Models.OrderItem;
import com.waitless.waitlesskitchenclient.Models.OrderList;
import com.waitless.waitlesskitchenclient.R;
import com.waitless.waitlesskitchenclient.ViewHolder.ItemViewHolder;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class ItemsAdapter extends RecyclerView.Adapter {

    private OrderList orders;
    private LayoutInflater inflatable;
    /* Parse in next adapter so that orderItem can be added to next status list */
    private ItemsAdapter nextAdapter;


    public ItemsAdapter(LayoutInflater inflatable, OrderList orders){
        this.inflatable = inflatable;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflatable.inflate(R.layout.item_card1, parent, false);
        String status = orders.getStatus();
        switch (status){
            case "New":
                v.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case "Preparing":
                v.setBackgroundColor(Color.parseColor("#03A9F4"));
                break;
            case "Ready":
                v.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        this.initStatus();
        final OrderItem orderItem = orders.order_items.get(position);

        ((ItemViewHolder)holder).setItemName(orderItem.getItemName());
        ((ItemViewHolder)holder).setQuantity(orderItem.getQuantity().toString());
        ((ItemViewHolder)holder).setTableNo(orderItem.getTable_no());
        /* Set Image */
        try {
            ((ItemViewHolder)holder).setImg(orderItem.getImageURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Set On Click Event*/
        if (!orders.getStatus().equals("Ready")) {
            holder.itemView.setOnClickListener(v -> {
                try {
                    /* PUT request to increment status on to server */
                    orderItem.incStatus();
                    /* Remove from current status list and add to next status list */
                    removeOrderItem(orderItem);
                    if (nextAdapter != null) nextAdapter.addOrderItem(orderItem);
                } catch (InterruptedException | JSONException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.order_items.size();
    }

    /**
     * Add order item into the list
     * @param orderItem item to be added
     */
    private void addOrderItem(OrderItem orderItem){
        orders.addOrderItem(orderItem);
        MainActivity.updateRecycleViews.run();
    }

    /**
     * Remove order item from the list
     * @param orderItem item to be remove
     */
    private void removeOrderItem(OrderItem orderItem){
        orders.removeOrderItem(orderItem);
        MainActivity.updateRecycleViews.run();
    }

    /**
     * Initialise Statuses
     */
    private void initStatus(){
        switch (orders.getStatus()){
            case "New":
                nextAdapter = MainActivity.preparingItemsAdapter;
                break;
            case "Preparing":
                nextAdapter = MainActivity.readyItemsAdapter;
                break;
            default:
                nextAdapter = null;
                break;
        }
    }

    /* Sorting */

    /**
     * Sort Order List by Table
     */
    public void sortByTable(){
        orders.order_items.sort(new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                return o1.getTable_no().compareTo(o2.getTable_no());
            }
        });
    }


    /**
     * Sort Order List by Time
     */
    public void sortByTime(){
        orders.order_items.sort(new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                if (o1.getTimeCreated() < o2.getTimeCreated()) return -1;
                if (o1.getTimeCreated() > o2.getTimeCreated()) return 1;
                return o1.getTable_no().compareTo(o2.getTable_no());
            }
        });
    }

    /* Grouping */

    /**
     * Group Order List by Items
     */
    public void groupByItem() {
        orders.order_items = orders.order_items.stream()
                .collect(groupingBy(OrderItem::getItemName, LinkedHashMap::new, toList()))
                .values().stream().flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Group Order List by Table
     */
    public void groupByTable() {
        orders.order_items = orders.order_items.stream()
                .collect(groupingBy(OrderItem::getTable_no, LinkedHashMap::new, toList()))
                .values().stream().flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }
}
