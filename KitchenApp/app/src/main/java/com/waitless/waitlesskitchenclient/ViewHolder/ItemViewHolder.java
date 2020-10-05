package com.waitless.waitlesskitchenclient.ViewHolder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.waitless.waitlesskitchenclient.R;

import java.util.HashMap;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView _itemName;
    private ImageView _pic;
    private TextView _quantity;
    private TextView _tableNo;
    /* Cache system so that same image doesn't need to be fetched twice */
    private static HashMap<String, Bitmap> imgCache;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        _itemName = (TextView) itemView.findViewById(R.id.ItemNameTextView);
        _pic = (ImageView) itemView.findViewById(R.id.imageView);
        _quantity = (TextView) itemView.findViewById(R.id.QuantityTextView);
        _tableNo = (TextView) itemView.findViewById(R.id.TableNoTextView);
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
}
