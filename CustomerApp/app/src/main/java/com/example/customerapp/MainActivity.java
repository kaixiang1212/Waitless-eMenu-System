package com.example.customerapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.customerapp.OrderCart.CartItem;
import com.example.customerapp.bill.BillFragment;
import com.example.customerapp.orders.OrdersFragment;
import com.example.customerapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

/**
 * Main activity for the app to start immediately following the setting of the table number.
 */
public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * This overridden method runs the customised setup processes on the creation of the SectionPagerAdapter Object
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    BillFragment billfrag = (BillFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 3);
                    billfrag.onResume();
                }
            }
        });
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = TableManager.getInstance().requestAssistance();
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Handles the button click to increase CartListItem quantity
     * @param v : This is the passed View
     */
    public void incQuantity(View v) {
        CartItem CI = (CartItem) v.getTag();
        OrdersFragment frag = sectionsPagerAdapter.getOrderstab();
        frag.increaseQuantity(CI);
    }

    /**
     * Handles the button click to decrease CartListItem quantity
     * @param v : This is the passed View
     */
    public void decQuantity(View v) {
        CartItem CI = (CartItem) v.getTag();
        OrdersFragment frag = sectionsPagerAdapter.getOrderstab();
        frag.decreaseQuantity(CI);
    }

    /**
     * Handles the button click to remove CartListItem from the cart
     * @param v : This is the passed View
     */
    public void removeCartItem(View v) {
        CartItem CI = (CartItem) v.getTag();
        OrdersFragment frag = sectionsPagerAdapter.getOrderstab();
        frag.removeCartItem(CI);
    }

    /**
     * Handles the button click to submit the order
     * @param v : This is the passed View
     */
    public void submitOrder(View v) {
        OrdersFragment frag = sectionsPagerAdapter.getOrderstab();
        try {
            frag.submitOrder();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}