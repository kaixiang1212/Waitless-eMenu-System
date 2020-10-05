package com.example.waiterapp.ViewHolder;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * OrderItemList View Holder to hold the CardView for each Order Item
 */
public class OrderItemListViewHolder extends RecyclerView.ViewHolder {
    private TextView _itemName;
    private ImageView _pic;
    private TextView _quantity;
    private TextView _tableNo;
    private CardView cardView;
    /* Cache system so that same image doesn't need to be fetched twice */
    private static HashMap<String, Bitmap> imgCache;

    private String status;

    public OrderItemListViewHolder(@NonNull View itemView) {
        super(itemView);
        _itemName = (TextView) itemView.findViewById(R.id.ItemNameTextView);
        _pic = (ImageView) itemView.findViewById(R.id.imageView);
        _quantity = (TextView) itemView.findViewById(R.id.QuantityTextView);
        _tableNo = (TextView) itemView.findViewById(R.id.TableNoTextView);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
    }

    public void setItemName(String text){
        _itemName.setText(text);
    }

    public void setQuantity(String quantity){
        _quantity.setText(quantity);
    }

    public void setImg(String path){
        Picasso.get().load(path).into(_pic);
    }

    public void setTableNo(String text){
        _tableNo.setText(text);
    }

    public String getStatus() {
        return status;
    }

    /**
     * Set the status of each OrderItem View and set the card background color accordingly.
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        switch (status){
            case "New":
                cardView.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case "Preparing":
                cardView.setBackgroundColor(Color.parseColor("#03A9F4"));
                break;
            case "Ready":
                cardView.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
        }
    }
}
