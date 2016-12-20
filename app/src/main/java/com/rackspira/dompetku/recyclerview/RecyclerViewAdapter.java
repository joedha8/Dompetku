package com.rackspira.dompetku.recyclerview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.R;
import com.rackspira.dompetku.model.AmbilData;

import java.util.List;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<AmbilData> ambilDatas;

    public RecyclerViewAdapter(Context context, List<AmbilData> ambilDatas) {
        this.context = context;
        this.ambilDatas = ambilDatas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ambilDatas.size();
    }
}
