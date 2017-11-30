package com.rackspira.epenting.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbHutang;
import com.rackspira.epenting.database.Hutang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kikiosha on 11/30/17.
 */

public class RecyclerViewAdapterPeminjaman extends RecyclerView.Adapter<RecyclerViewHolderPeminjaman> {
    private Context context;
    private List<Hutang> hutangList=new ArrayList<>();
    DbHutang dbHutang;
    LayoutInflater inflater;

    public RecyclerViewAdapterPeminjaman(Context context, List<Hutang> hutangList) {
        this.context = context;
        this.hutangList = hutangList;
        inflater=LayoutInflater.from(context);
        dbHutang=DbHutang.geiInstance(context);
    }

    @Override
    public RecyclerViewHolderPeminjaman onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_hutang, parent, false);
        RecyclerViewHolderPeminjaman viewHolderPeminjaman=new RecyclerViewHolderPeminjaman(view);

        return viewHolderPeminjaman;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderPeminjaman holder, int position) {
        Hutang hutang=hutangList.get(position);

        holder.textViewTanggalKembali.setText(hutang.getTgl_kembali());
        holder.textViewPemberiPinjaman.setText(hutang.getPemberiPinjaman());
        holder.textViewNominalPinjaman.setText(hutang.getNominal());
    }

    @Override
    public int getItemCount() {
        return hutangList.size();
    }
}
