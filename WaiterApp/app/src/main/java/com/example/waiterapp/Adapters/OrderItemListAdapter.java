package com.example.waiterapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.Models.OrderItem;
import com.example.waiterapp.Models.OrderItemList;
import com.example.waiterapp.R;
import com.example.waiterapp.ViewHolder.OrderItemListViewHolder;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * OrderItemListAdapter to bind OrderItems to CardLayout in RecyclerView
 */
public class OrderItemListAdapter extends RecyclerView.Adapter {

    private OrderItemList orderItemList;
    private LayoutInflater layoutInflater;

    /**
     * Constructor for OrderItemListAdapter
     * @param readyToDeliverItemList
     * @param layoutInflater
     */
    public OrderItemListAdapter(OrderItemList readyToDeliverItemList, LayoutInflater layoutInflater) {
        this.orderItemList = readyToDeliverItemList;
        this.layoutInflater = layoutInflater;
    }

    /**
     * onCreateViewHolder - Overridden method to fetch Card Layout
     * @param parent
     * @param viewType
     * @return Object of OrderItemListViewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_card1, parent, false);
        return new OrderItemListViewHolder(v);
    }

    /**
     * onBindViewHolder - Overridden method to set data in OrderItemListViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.orderItemList.get(position);
        ((OrderItemListViewHolder) holder).setTableNo(orderItem.getTableNo());
        ((OrderItemListViewHolder) holder).setItemName(orderItem.getItemName());
        ((OrderItemListViewHolder) holder).setQuantity(orderItem.getQuantity());
        ((OrderItemListViewHolder) holder).setStatus(orderItem.getStatus());

        try {
            ((OrderItemListViewHolder)holder).setImg(orderItem.getImageURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (orderItemList.getStatus().equals("Ready")) {
            holder.itemView.setOnClickListener(v -> {
                try {
                    /* PUT request to increment status on to server */
                    orderItem.incStatus();
                    /* Remove from current list */
                    removeOrderItem(orderItem);
                } catch (InterruptedException | JSONException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * getItemCount - Overridden method to fetch OrderItemList size
     * @return orderItemList.size()
     */
    @Override
    public int getItemCount() {
        return orderItemList.orderItemList.size();
    }

    /**
     * getItemViewType - Overridden method to fetch cardLayout
     * @param position
     * @return cardLayout
     */
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_card1;
    }

    /**
     * addOrderItem - add new orderItem to existing orderItemList
     * @param orderItem
     */
    private void addOrderItem(OrderItem orderItem) {
        orderItemList.addOrderItem(orderItem);
        notifyDataSetChanged();
    }

    /**
     * removeOrderItem - remove an orderItem from orderItemList
     * @param orderItem
     */
    private void removeOrderItem(OrderItem orderItem) {
        orderItemList.removeOrderItem(orderItem);
        notifyDataSetChanged();
    }
}
