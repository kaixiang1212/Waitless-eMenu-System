package com.example.customerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.data.Item;
import com.example.customerapp.menu.ItemViewHolder;
import com.example.customerapp.ui.main.SectionsPagerAdapter;

import java.util.List;

/**
 * This adapter is for displaying menu items as cards in each categories
 */
public class ItemsAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflatable;
    private List<Item> itemList;
    private SectionsPagerAdapter sPA;

    /**
     * Public constructor
     * @param context : Android system context object
     */
    public ItemsAdapter(Context context) {
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
        View v = inflatable.inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(v);
    }

    /**
     * Code to run on the binding of the ViewHolder, Initialises the Viewholder with item data
     * @param holder : the view holder to be bound
     * @param position : position of the order item to be bound
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item currItem = this.itemList.get(position);
        ItemViewHolder newIVH = ((ItemViewHolder)holder);
        newIVH.setSPA(this.sPA);
        newIVH.setText(currItem.getTitle());
        newIVH.setCost(Double.toString(currItem.getPrice()));
        newIVH.setPic("http://10.0.2.2:8080/images/" + currItem.getImagePath());
        newIVH.setPosition(position);
        newIVH.setItem(currItem);
    }

    /**
     * Gets the number of Items
     * @return itemList.size() : the number of items
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Gets the Item List
     * @param itemList : item list to send
     */
    public void sendIList(List<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * Sets the reference for the SectionPagerAdapter necessary for the interfragment communication
     * @param sPA : SectionPagerAdapter Object
     */
    public void setSPA(SectionsPagerAdapter sPA){
        this.sPA = sPA;
    }
}
