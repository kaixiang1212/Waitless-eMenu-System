package com.example.customerapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.data.OrderItem;
import com.example.customerapp.home.OrderStatusViewHolder;

import java.util.ArrayList;

/**
 * This object Adapts the OrderStatus layout and views with the OrderFragment
 */
public class OrderStatusAdapter extends RecyclerView.Adapter {

    private ArrayList<OrderItem> orderItems;
    private LayoutInflater inflatable;

    /**
     * Public Contructor
     * @param orderItems : orderItems to adapt
     * @param inflater : Android layout inflator
     */
    public OrderStatusAdapter(ArrayList<OrderItem> orderItems, LayoutInflater inflater){
        this.orderItems = orderItems;
        this.inflatable = inflater;
    }

    /**
     * Code to run on the creation of the ViewHolder
     * @param parent : Android system ViewGroup object
     * @param viewType : Android system ViewType number
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflatable.inflate(R.layout.order_status_item_view, parent, false);
        return new OrderStatusViewHolder(v);
    }

    /**
     * Code to run on the binding of the ViewHolder
     * @param holder : the view holder to be bound
     * @param position : position of the order item to be bound
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((OrderStatusViewHolder) holder).bindData(orderItems.get(position));
    }

    /**
     * Gets the count of items
     * @return orderItems.size() : the number of order items
     */
    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    /**
     * Notifies Adapter of a change in dataset
     */
    public void notifyAdapter(){
        this.notifyDataSetChanged();
    }
}
