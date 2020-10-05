package com.example.customerapp.menu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;

/**
 * This class is to hold and keep a CategoryView object
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private catClickListener cListener;
    private ToggleButton _tb;
    private int position;

    /**
     * Sets the position
     * @param position : position of the itemView
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * The interface required for intrafragment communication
     */
    public interface catClickListener {
        void onCatClick(int position, CategoryViewHolder cVH);
    }

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     */
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        _tb = (ToggleButton) itemView.findViewById(R.id.catButton);
    }

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     * @param mFReference : reference to the MenuFragment Object
     */
    public CategoryViewHolder(@NonNull View itemView, MenuFragment mFReference) {
        super(itemView);
        _tb = (ToggleButton) itemView.findViewById(R.id.catButton);
        this.cListener = (catClickListener) mFReference;
        _tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cListener.onCatClick(position, CategoryViewHolder.this);
            }
        });
    }

    /**
     * Turns off the Toggle Button
     */
    public void setTBOff(){
        _tb.setChecked(false);
    }

    /**
     * Turns on the Toggle Button
     */
    public void setTBOn(){
        _tb.setChecked(true);
    }

    /**
     * Gets the text of the Toggle Button
     */
    public CharSequence getText() {
        return _tb.getText();
    }

    /**
     * Sets the text of the Toggle Button
     */
    public void setText(String text) {
        _tb.setText(text);
        _tb.setTextOff(text);
        _tb.setTextOn(text);
    }
}
