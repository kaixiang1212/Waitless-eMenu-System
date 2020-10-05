package com.example.customerapp.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.customerapp.Adapters.CartAdapter;
import com.example.customerapp.OrderCart.CartItem;
import com.example.customerapp.OrderCart.CartList;
import com.example.customerapp.ApiRequests.PostApiRequest;
import com.example.customerapp.R;
import com.example.customerapp.TableManager;
import com.example.customerapp.data.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * This is the Orders tab of the app
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View _v;
    private CartList _CL;
    private CartAdapter _CA;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This overridden method runs the customised setup processes on the creation of the OrderFragment Object
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        _CL = new CartList();
        _CA = new CartAdapter(_CL);
    }

    /**
     *This overridden method runs the customised setup processes on the creation of the View of the OrderFragment Fragment object
     * @param inflater : This is the android system for inflating the linked layout
     * @param container : This is the android system for containment of the view
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     * @return _v : the view for the orders tab
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        _v = v;

        //This is the view for orders
        RecyclerView rV = v.findViewById(R.id.orderList);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(new LinearLayoutManager(getContext()));
        rV.setAdapter(_CA);
        updateCartSummary();

        return _v;
    }

    /**
     * Updates the summary text in the Orders tab
     */
    public void updateCartSummary() {
        TextView instructions = _v.findViewById(R.id.orderInstructions);
        TextView price = _v.findViewById(R.id.cartTotal);
        Button submit = _v.findViewById(R.id.submitOrder);

        if (_CL.isEmpty()) {
            instructions.setText("Add items from the MENU tab.");
            price.setText("");
            submit.setEnabled(false);
        } else {
            instructions.setText("View and edit your order before submitting.\n\nYour total is:");
            price.setText(String.format("$%.2f", _CL.cartTotal()));
            submit.setEnabled(true);
        }
    }

    /**
     * Updates the summary text in the Orders tab following order submission
     */
    public void clearCartSummary() {
        TextView instructions = _v.findViewById(R.id.orderInstructions);
        TextView price = _v.findViewById(R.id.cartTotal);
        Button submit = _v.findViewById(R.id.submitOrder);

        instructions.setText("Your order has been placed.\n\nYou can view the status of your items from the STATUS tab.\n\nYou can add more items from the MENU tab.");
        price.setText("");
        submit.setEnabled(false);
    }

    /**
     * Adds an Item to the order CartList and updates the View
     * @param item : the Item to be added
     */
    public void addToOrder(Item item){
        _CL.addToCartList(item);
        _CA.notifyAdapter();
        updateCartSummary();
    }

    /**
     * Increase quantity of a CartItem and updates the View
     * @param cItem : the CartItem to be incremented
     */
    public void increaseQuantity(CartItem cItem) {
        cItem.incQuantity();
        _CA.notifyDataSetChanged();
        updateCartSummary();
    }

    /**
     * Decrease the quantity of a CartItem and updates the View
     * @param cItem : the CartItem to be decremented
     */
    public void decreaseQuantity(CartItem cItem) {
        if (cItem.getQuantity() == 1) {
            _CL.removeFromCartList(cItem);
        } else {
            cItem.decQuantity();
        }
        _CA.notifyDataSetChanged();
        updateCartSummary();
    }

    /**
     * Remove a CartItem from the CartList and updates the View
     * @param cItem : the CartItem to be removed
     */
    public void removeCartItem(CartItem cItem) {
        _CL.removeFromCartList(cItem);
        _CA.notifyDataSetChanged();
        updateCartSummary();
    }

    /**
     * Submits an order to the server.
     * Using the current CartList, produces a JSONArray, and makes a payload using
     * the table number and this JSONArray.
     * Makes HTTP request to server API.
     * Upon success, clears the CartList and updates the View
     * @throws JSONException
     */
    public void submitOrder() throws JSONException {
        JSONArray items = _CL.itemAndQuantity();
        JSONObject data = new JSONObject();
        try {
            Integer table_no = TableManager.getInstance().getTableNo();
            data.put("table_no", table_no.toString());
            data.put("items", items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String urlString = "http://10.0.2.2:8080" + "/customer/order";
        String jsonString = data.toString();

        String response = "";
        try {
            response = new PostApiRequest().execute(urlString, jsonString).get();
            System.out.println(response);
            _CL.emptyCartList();
            _CA.notifyDataSetChanged();
            clearCartSummary();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
