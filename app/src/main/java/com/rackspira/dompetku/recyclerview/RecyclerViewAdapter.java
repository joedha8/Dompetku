package com.rackspira.dompetku.recyclerview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.model.AmbilData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<DataMasuk> ambilDatas = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<DataMasuk> ambilDatas) {
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
            holder.keterangan.setText(ambilDatas.get(position).ket);
            holder.nominal.setText(ambilDatas.get(position).biaya);
    }

    @Override
    public int getItemCount() {
        return ambilDatas.size();
    }
}
