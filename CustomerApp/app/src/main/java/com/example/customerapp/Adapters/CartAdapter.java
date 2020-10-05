package com.example.customerapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.OrderCart.CartItemViewHolder;
import com.example.customerapp.OrderCart.CartList;
import com.example.customerapp.R;
import com.example.customerapp.data.Item;
import com.example.customerapp.menu.ItemViewHolder;

/**
 * Adapter to dynamically populate a recyclerview with items currently in the order cart
 */
public class CartAdapter extends RecyclerView.Adapter implements ItemViewHolder.ItemClickListener {
    private CartList cartList;

    /**
     * Public Constructor for CartAdapter
     * @param cl : the CartList the adapter will be adapting
     */
    public CartAdapter(CartList cl) {
        this.cartList = cl;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CartItemViewHolder(view);
    }

    /**
     * Code to run on the binding of the ViewHolder, Initialises the Viewholder with item data
     * @param holder : the view holder to be bound
     * @param position : position of the order item to be bound
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CartItemViewHolder) holder).bindData(cartList.cart_list.get(position));
    }

    /**
     * Gets the number of Items
     * @return cartList.cart_list.size() : the number of items
     */
    @Override
    public int getItemCount() {
        return cartList.cart_list.size();
    }

    /**
     * Gets the itemview type
     * @param position : position of the itemview in cart
     * @return cart_item_view : type of item view
     */
    @Override
    public int getItemViewType(int position) {
        return R.layout.cart_item_view;
    }

    /**
     * The handler for interfragment communication triggered on click of an itemViewHolder
     * @param item : the item that is clicked
     */
    @Override
    public void onItemClick(Item item) {
        cartList.addToCartList(item);
        this.notifyDataSetChanged();
    }

    /**
     * Notifies the adapter that the dataset has been changed
     */
    public void notifyAdapter(){
        this.notifyDataSetChanged();
    }
}
