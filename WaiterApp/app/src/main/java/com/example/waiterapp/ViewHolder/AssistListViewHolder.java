package com.example.waiterapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;

/**
 * View Holder class to hold the view objects for Customer
 * Assistance Request
 */
public class AssistListViewHolder extends RecyclerView.ViewHolder {

    private TextView _table;
    private Button _button;

    public AssistListViewHolder(@NonNull View itemView) {
        super(itemView);
        _table = (TextView) itemView.findViewById(R.id.table_no);
        _button = (Button) itemView.findViewById(R.id.button);
    }

    /**
     * Set the table number as the button's tag
     *
     * @param table_no
     */
    public void setTable(String table_no) {
        _table.setText("Table: " + table_no);
        _button.setTag(Integer.parseInt(table_no));
    }
}
