package com.example.customerapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.customerapp.bill.BillFragment;
import com.example.customerapp.data.Item;
import com.example.customerapp.home.HomeFragment;
import com.example.customerapp.menu.ItemViewHolder;
import com.example.customerapp.menu.MenuFragment;
import com.example.customerapp.orders.OrdersFragment;

/**
 * A Customised FragmentPagerAdapter object that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements ItemViewHolder.ItemClickListener {

    private static final String[] TAB_TITLES = new String[]{"Menu", "Cart","Status", "Bill"};
    private final Context mContext;
    private Fragment hometab = new HomeFragment();
    private MenuFragment menutab = new MenuFragment();
    private OrdersFragment orderstab = new OrdersFragment();
    private Fragment billtab = new BillFragment();

    /**
     * Constructor for the SectionsPagerAdapter Fragment
     * @param context : This is the android system for finding the related context of the application
     * @param fm : This is the android system for managing fragments.
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Returns the corresponding fragment object when the different tabs are pressed at the top of app OR swiped between.
     * @param position : is the position of the tab title pressed in the top of the app
     * @return : the correct fragment that corresponds to the correct tab heading pressed.
     */
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = menutab;
                menutab.setSPA(this);
                break;
            case 1:
                frag = orderstab;
                break;
            case 2:
                frag = hometab;
                break;
            case 3:
                frag = billtab;
                break;
            default:
                break;
        }
        return frag;
    }

    /**
     * Getter method for the Page title of a tab given it's position
     * @param position : the position of the tab on the top navigation bar.
     * @return TAB_TITLES[position] : the correct tab title for the position.
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    /**
     * Getter method for the total number of tabs
     * @return 4 : a fixed value for the 4 tabs designed and implemented in line with our proposal
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * Handles interfragment communication upon clicking of the menu item to be added to the orders
     * @param item : Item that was clicked and to be passed to the order.
     */
    @Override
    public void onItemClick(Item item) {
        this.orderstab.addToOrder(item);
    }

    /**
     * Getter method for the orderstab fragment
     * @return orderstab : An OrderFragment object.
     */
    public OrdersFragment getOrderstab() {return orderstab;}
}