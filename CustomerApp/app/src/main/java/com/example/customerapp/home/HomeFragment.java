package com.example.customerapp.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.customerapp.Adapters.OrderStatusAdapter;
import com.example.customerapp.ApiRequests.AsyncTaskResult;
import com.example.customerapp.ApiRequests.GetApiRequest;
import com.example.customerapp.R;
import com.example.customerapp.TableManager;
import com.example.customerapp.data.OrderStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is the Home tab of the app
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static private ScheduledExecutorService executor;
    private String mParam1;
    private String mParam2;
    private View _v;
    private OrderStatus orderStatus;
    private OrderStatusAdapter _pendingA;
    private OrderStatusAdapter _preparingA;
    private OrderStatusAdapter _readyA;
    private OrderStatusAdapter _deliveredA;
    public Button refreshButton;

    /**
     * Updates the status of Order
     */
    private Runnable updateStatus = new Runnable() {
        @Override
        public void run() {
            Objects.requireNonNull(getActivity()).runOnUiThread(update);
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This overridden method runs the customised setup processes on the creation of the HomeFragment Object
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        orderStatus = new OrderStatus();
        _pendingA = new OrderStatusAdapter(orderStatus.New, getLayoutInflater());
        _preparingA = new OrderStatusAdapter(orderStatus.Preparing, getLayoutInflater());
        _readyA = new OrderStatusAdapter(orderStatus.Ready, getLayoutInflater());
        _deliveredA = new OrderStatusAdapter(orderStatus.Done, getLayoutInflater());
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateStatus, 0, 10, TimeUnit.SECONDS);
    }

    /**
     *This overridden method runs the customised setup processes on the creation of the View of the HomeFragment Fragment object
     * @param inflater : This is the android system for inflating the linked layout
     * @param container : This is the android system for containment of the view
     * @param savedInstanceState : This is the object for storing the instance state data as generated, called and passed by the android system.
     * @return _v : the view for the home tab
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _v = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rV1 = _v.findViewById(R.id.pendingRView);
        RecyclerView rV2 = _v.findViewById(R.id.preparingRView);
        RecyclerView rV3 = _v.findViewById(R.id.readyRView);
        RecyclerView rV4 = _v.findViewById(R.id.deliveredRView);
        rV1.setLayoutManager(new LinearLayoutManager(getContext()));
        rV2.setLayoutManager(new LinearLayoutManager(getContext()));
        rV3.setLayoutManager(new LinearLayoutManager(getContext()));
        rV4.setLayoutManager(new LinearLayoutManager(getContext()));
        rV1.setAdapter(_pendingA);
        rV2.setAdapter(_preparingA);
        rV3.setAdapter(_readyA);
        rV4.setAdapter(_deliveredA);
        refreshButton = _v.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus.run();
            }
        });
        return _v;
    }

    /**
     * Fetch order status from server and update UI to reflect changes
     */
    private Runnable update = new Runnable(){
        @Override
        public void run() {
            OrderStatus newStatus = getOrderStatus();
            if (newStatus == null) return;
            orderStatus.New.clear();
            orderStatus.New.addAll(newStatus.New);
            orderStatus.Preparing.clear();
            orderStatus.Preparing.addAll(newStatus.Preparing);
            orderStatus.Ready.clear();
            orderStatus.Ready.addAll(newStatus.Ready);
            orderStatus.Done.clear();
            orderStatus.Done.addAll(newStatus.Done);
            _pendingA.notifyDataSetChanged();
            _preparingA.notifyDataSetChanged();
            _readyA.notifyDataSetChanged();
            _deliveredA.notifyDataSetChanged();
        }
    };

    /**
     * Sends a get request to fetch the current progresses of the ordered items
     * @return OrderStatus object
     */
    private OrderStatus getOrderStatus() {
        String tableNo = TableManager.getInstance().getTableNo().toString();
        String urlString = "http://10.0.2.2:8080" + "/customer/order/" + tableNo;
        AsyncTaskResult<String> result;
        try {
             result = new GetApiRequest().execute(urlString, "").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (result.getError() != null){
            return null;
        }
        String jsonString = result.getResult();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(jsonString, OrderStatus.class);
    }
}
