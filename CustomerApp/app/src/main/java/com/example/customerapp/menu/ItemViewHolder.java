package com.example.customerapp.menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.TableManager;
import com.example.customerapp.data.Item;
import com.example.customerapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

/**
 * This class is to hold and keep an ItemView object
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView _tv;
    private TextView _cost;
    private ImageView _pic;
    private ConstraintLayout _card;
    private int position;
    private Item _item;
    private SectionsPagerAdapter sPA;

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     */
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        _tv = (TextView) itemView.findViewById(R.id.textDishLabel);
        _pic = (ImageView) itemView.findViewById(R.id.imgView);
        _cost = (TextView) itemView.findViewById(R.id.textDishCost);
        _card = (ConstraintLayout) itemView.findViewById(R.id.itemCardLayout);
        _card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemClickListener iCL = ItemViewHolder.this.sPA;
                iCL.onItemClick(ItemViewHolder.this._item);
                String message = ItemViewHolder.this._tv.getText() + " added to cart";
                Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Setter method for SectionsPagerAdapter
     * @param sPA : SectionsPagerAdapter Object
     */
    public void setSPA(SectionsPagerAdapter sPA) {
        this.sPA = sPA;
    }

    /**
     * The interface required for interfragment communication
     */
    public interface ItemClickListener {
        void onItemClick(Item item);
    }

    /**
     * Getter method for textView's text
     * @return _tv.getText(): the text in textView
     */
    public CharSequence getText() {
        return _tv.getText();
    }

    /**
     * Setter method for textView's text
     * @param text :
     */
    public void setText(String text) {
        _tv.setText(text);
    }

    /**
     * Sets the cost
     * @param text : the cost
     */
    public  void setCost(String text) {
        _cost.setText("$"+text);
    }

    /**
     * Sets the picture
     * @param path : picture pathname to use in api request
     */
    public void setPic(String path) {
        Picasso.get().load(path).into(_pic);
    }

    /**
     * Sets the position
     * @param position : position of the itemView
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the item for the ItemViewHolder
     * @param i : Item Object
     */
    public void setItem(Item i) { this._item = i; }
}
