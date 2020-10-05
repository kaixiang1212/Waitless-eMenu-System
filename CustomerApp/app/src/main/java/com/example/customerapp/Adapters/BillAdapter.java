package com.example.customerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.bill.BillViewHolder;
import com.example.customerapp.data.BillItem;

import java.util.List;

/**
 * This object Adapts the Bill layout and view with the BillFragment
 */
public class BillAdapter extends RecyclerView.Adapter {

    private List<BillItem> _bill;
    private LayoutInflater inflatable;

    /**
     * Public Constructor
     * @param context : Android system context object
     * @param bill : the bill item to adapt
     */
    public BillAdapter(Context context, List<BillItem> bill) {
        _bill = bill;
        inflatable = LayoutInflater.from(context);
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
        View v = inflatable.inflate(R.layout.bill_card, parent, false);
        return new BillViewHolder(v);
    }

    /**
     * Code to run on the binding of the ViewHolder, Initialises the Viewholder with item data
     * @param holder : the view holder to be bound
     * @param position : position of the order item to be bound
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BillViewHolder newBVH = ((BillViewHolder)holder);
        BillItem it = _bill.get(position);
        newBVH.setText(it.getQty(), it.getTitle(), it.getCost(), it.getCostTotal());
    }

    /**
     * Gets the number of Items
     * @return _bill.size() : the number of items
     */
    @Override
    public int getItemCount() {
        return _bill.size();
    }
}
