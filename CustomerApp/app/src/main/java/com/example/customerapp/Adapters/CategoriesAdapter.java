package com.example.customerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import com.example.customerapp.menu.CategoryViewHolder;
import com.example.customerapp.menu.MenuFragment;

/**
 * This is for displaying menu categories as toggle buttons in the menu
 */
public class CategoriesAdapter extends RecyclerView.Adapter {

    private String[] mDataset;
    private LayoutInflater inflatable;
    private MenuFragment mFReference;
    private boolean defaultTBSet;

    /**
     * Public Constructor
     * @param context : Androd system context object
     * @param myDataset : set of data to be parsed
     */
    public CategoriesAdapter(Context context, String[] myDataset) {
        mDataset = myDataset;
        inflatable = LayoutInflater.from(context);
    }

    /**
     * Public Constructor
     * @param context : Androd system context object
     * @param myDataset : set of data to be parsed
     * @param menuFragment : A MenuFragment Object
     */
    public CategoriesAdapter(Context context, String[] myDataset, MenuFragment menuFragment) {
        mDataset = myDataset;
        inflatable = LayoutInflater.from(context);
        this.mFReference = menuFragment;
        this.defaultTBSet = false;
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
        View v = inflatable.inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(v, mFReference);
    }

    /**
     * Code to run on the binding of the ViewHolder, Initialises the Viewholder with item data
     * @param holder : the view holder to be bound
     * @param position : position of the order item to be bound
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder newCVH = ((CategoryViewHolder)holder);
        newCVH.setText(mDataset[position]);
        newCVH.setPosition(position);
        if (this.defaultTBSet == false){
            newCVH.setTBOn();
            this.defaultTBSet = true;
            mFReference.setPreviousCVH(newCVH);
        }
    }

    /**
     * Gets the number of Items
     * @return mDataset.length() : the number of items
     */
    @Override
    public int getItemCount() {
        return mDataset.length;
    }



}
