package com.example.customerapp.home;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.TableManager;
import com.example.customerapp.data.Item;
import com.example.customerapp.data.OrderItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is to hold and keep an OrderStatusView object
 */
public class OrderStatusViewHolder extends RecyclerView.ViewHolder {
    private TextView itemName;
    private TextView quantity;
    private ImageView imageView;
    private TextView timestamp;

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     */
    public OrderStatusViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.itemName);
        quantity = (TextView) itemView.findViewById(R.id.quantity);
        imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        timestamp = (TextView) itemView.findViewById(R.id.timestamp);

    }

    /**
     * Binds the data from orderItem to the View
     * @param orderItem : the OrderItem Object to get the data from
     */
    public void bindData(final OrderItem orderItem){
        itemName.setText(orderItem.getItemName());
        quantity.setText(orderItem.getQuantity());
        TableManager tableManager = TableManager.getInstance(1);
        Item item = tableManager.getMenu().getItemByName(orderItem.getItemName());
        Picasso.get().load("http://10.0.2.2:8080/images/" + item.getImagePath()).into(imageView);
        Float ftime = orderItem.getTimestamp() * 1000;
        long time = ftime.longValue();
        String pattern = "hh:mm a";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        String dateString = simpleDateFormat.format(date);
        timestamp.setText(dateString);
    }
}
