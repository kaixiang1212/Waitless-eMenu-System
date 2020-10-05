package com.example.customerapp.bill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customerapp.Adapters.BillAdapter;
import com.example.customerapp.R;
import com.example.customerapp.TableManager;
import com.example.customerapp.data.BillItem;

import java.util.List;

/**
 * This is the Bill tab of the app
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View _v;
    private List<BillItem> bill;
    private BillAdapter _bA;
    private String mParam1;
    private String mParam2;

    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillFragment.
     */
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This overridden method runs the customised setup processes on the creation of the BillFragment Object
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
     *This overridden method runs the customised setup processes on the creation of the View of the BillFragment Fragment object
     * @param inflater : This is the android system for inflating the linked layout
     * @param container : This is the android system for containment of the view
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     * @return _v : the view for the bill tab
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _v = inflater.inflate(R.layout.fragment_bill, container, false);
        return _v;
    }

    /**
     * his overridden method refreshes the bill when the user switches back to the Bill tab
     */
    @Override
    public void onResume() {
        super.onResume();
        TableManager mM = TableManager.getInstance(1);
        bill = mM.getBill(4);
        this._bA = new BillAdapter(getContext(), bill);
        RecyclerView rV = _v.findViewById(R.id.billList);
        rV.setAdapter(_bA);
        rV.setLayoutManager(new LinearLayoutManager(getContext()));
        rV.setItemAnimator(new DefaultItemAnimator());
        double total = 0;
        for (BillItem bI: bill) {
            total += Double.parseDouble(bI.getCostTotal());
        }
        TextView totals = _v.findViewById(R.id.textBillTotal);
        totals.setText("Total: $" + String.format("%.2f", total));
    }
}
