package com.example.customerapp.bill;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;

/**
 * This class is to hold and keep a BillView object
 */
public class BillViewHolder extends RecyclerView.ViewHolder {

    private TextView _qty;
    private TextView _name;
    private TextView _cost;
    private TextView _total;

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     */
    public BillViewHolder(@NonNull View itemView) {
        super(itemView);
        _qty = (TextView) itemView.findViewById(R.id.textQty);
        _name = (TextView) itemView.findViewById(R.id.textDesc);
        _cost = (TextView) itemView.findViewById(R.id.textCost);
        _total = (TextView) itemView.findViewById(R.id.textCostTotal);
    }

    /**
     * Sets the text for the bill
     * @param qty : quantity of the item for the bill
     * @param name : name of the item for the bill
     * @param cost : price of the item for the bill
     * @param total : total price of the item for the bill
     */
    public void setText(String qty, String name, String cost, String total) {
        _qty.setText(qty);
        _name.setText(name);
        _cost.setText("$"+cost);
        _total.setText("$"+total);
    }
}
