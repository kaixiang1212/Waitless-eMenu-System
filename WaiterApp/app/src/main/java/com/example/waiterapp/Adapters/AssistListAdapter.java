package com.example.waiterapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.Models.AssistList;
import com.example.waiterapp.Models.AssistListItem;
import com.example.waiterapp.R;
import com.example.waiterapp.ViewHolder.AssistListViewHolder;

/**
 * Adapter to dynamically populate a recylerview with Asisst Requests
 */
public class AssistListAdapter extends RecyclerView.Adapter {

    private AssistList assists;
    private LayoutInflater inflater;

    /**
     * Constructor for AssistListAdapter
     * @param in - layout inflater
     * @param al - the AssistList
     */
    public AssistListAdapter (LayoutInflater in, AssistList al) {
        this.assists = al;
        this.inflater = in;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = inflater.inflate(R.layout.assist_list_item, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new AssistListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssistListItem ali = assists.assist_list.get(position);
        ((AssistListViewHolder)holder).setTable(ali.getTable());
    }

    @Override
    public int getItemCount() {
        return assists.assist_list.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.assist_list_item;
    }
}
