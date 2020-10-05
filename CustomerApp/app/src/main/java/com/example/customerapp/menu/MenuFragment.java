package com.example.customerapp.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customerapp.Adapters.CategoriesAdapter;
import com.example.customerapp.Adapters.ItemsAdapter;
import com.example.customerapp.TableManager;
import com.example.customerapp.R;
import com.example.customerapp.data.Category;
import com.example.customerapp.data.Item;
import com.example.customerapp.data.Menu;
import com.example.customerapp.ui.main.SectionsPagerAdapter;

import java.util.List;

/**
 * This is the Menu tab of the app
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements CategoryViewHolder.catClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View _v;
    private List<Category> menuCategories;

    private String mParam1;
    private String mParam2;
    private CategoryViewHolder selectedCVH;
    private CategoryViewHolder previousCVH;
    private ItemsAdapter iA;
    private SectionsPagerAdapter sPA;
    private int tableNo;

    public MenuFragment() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This overridden method runs the customised setup processes on the creation of the MenuFragment Object
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     *This overridden method runs the customised setup processes on the creation of the View of the MenuFragment Fragment object
     * @param inflater : This is the android system for inflating the linked layout
     * @param container : This is the android system for containment of the view
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     * @return _v : the view for the menu tab
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TableManager mM = TableManager.getInstance();
        Menu newMenu = mM.getMenu();
        this.menuCategories = newMenu.getCategories();
        this.iA = new ItemsAdapter(getContext());
        String[] newArray = new String[menuCategories.size()];
        int i = 0;
        int total_items = 0; // including duplicates
        for (Category c : menuCategories) {
            newArray[i] = c.getTitle();
            total_items += c.size();
            i++;
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        _v = v;
        //This is the view for categories
        RecyclerView rV = v.findViewById(R.id.catList);
        CategoriesAdapter cA = new CategoriesAdapter(getContext(), newArray, this);
        rV.setAdapter(cA);
        rV.setLayoutManager(new LinearLayoutManager(getContext()));
        rV.setItemAnimator(new DefaultItemAnimator());
        getItemsForCategoryPosition(0);
        return v;
    }

    /**
     * This is the Handler for the Intrafragment communication required for the menu to update once at category has been selected
     * @param position : the position of the category for the loading of the correct items related to selected category
     * @param cVH : CategoryViewHolder object control the toggle button status
     */
    @Override
    public void onCatClick(int position, CategoryViewHolder cVH) {
        getItemsForCategoryPosition(position);
        if (this.previousCVH != null){
            this.previousCVH.setTBOff();
        }
        this.previousCVH = cVH;
    }

    /**
     * Private method to get the items for a category given a category position
     * @param position : position of the category
     */
    private void getItemsForCategoryPosition(int position) {
        RecyclerView rv2 = _v.findViewById(R.id.itemList);
        int j = 0;
        List<Item> itemList = this.menuCategories.get(position).getItems();
        String[] newerArray = new String[itemList.size()];
        for (Item i : itemList){
            newerArray[j] = i.getTitle();
            j++;
        }
        this.iA.sendIList(itemList);
        this.iA.setSPA(this.sPA);
        rv2.setAdapter(iA);
        rv2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv2.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * sets the previous CategoryViewHolderObject so that it's toggle button status can be updated
     * @param newCVH : the previous CategoryViewHolder Object
     */
    public void setPreviousCVH(CategoryViewHolder newCVH) {
        this.previousCVH = newCVH;
    }

    /**
     * Sets the SPA Object required for interfragment communication.
     * @param sPA : SectionPagerAdapter Object
     */
    public void setSPA(SectionsPagerAdapter sPA){
        this.sPA = sPA;
    }
}
