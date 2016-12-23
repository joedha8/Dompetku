package com.rackspira.dompetku.recyclerview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DataMasuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<DataMasuk> dataMasuks = new ArrayList<>();
    LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<DataMasuk> dataMasuks1) {
        this.context = context;
        this.dataMasuks = dataMasuks1;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.list_view, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataMasuk dataMasuk = dataMasuks.get(position);
        holder.keterangan.setText(dataMasuk.getKet());
        holder.pemasukkan_head.setText(dataMasuk.getStatus());
        holder.nominal.setText("Rp. " +dataMasuk.getBiaya()+",00");
        
        if( dataMasuk.getStatus().equals("Pemasukkan")){
            holder.gambar.setImageResource(R.drawable.ic_circle_plus_green);
        }else
            holder.gambar.setImageResource(R.drawable.ic_circle_minus_red);
    }

    @Override
    public int getItemCount() {

        return dataMasuks.size();
    }
}