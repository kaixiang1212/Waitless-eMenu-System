package com.example.customerapp.OrderCart;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;

/**
 * This class is to hold and keep A CartItemView object
 */
public class CartItemViewHolder extends RecyclerView.ViewHolder {

    private TextView itemName;
    private Button remove;
    private Button increase;
    private Button decrease;
    private TextView quantity;

    /**
     * This is the public constructor
     * @param itemView : view as passed in from android system
     */
    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.item_name);
        remove = (Button) itemView.findViewById(R.id.remove);
        increase = (Button) itemView.findViewById(R.id.increase);
        decrease = (Button) itemView.findViewById(R.id.decrease);
        quantity = (TextView) itemView.findViewById(R.id.quantity);
    }

    /**
     * Binds data of the CartItem to the view
     * @param cartItem : the cartItem to get the data from to reflect in the view in the frontend of the app
     */
    public void bindData(final CartItem cartItem) {
        itemName.setText(String.format("%s ($%.2f)",cartItem.getItem().getTitle(),cartItem.getItem().getPrice()));
        quantity.setText(cartItem.getQuantity().toString());
        remove.setTag(cartItem);
        increase.setTag(cartItem);
        decrease.setTag(cartItem);
    }
}
