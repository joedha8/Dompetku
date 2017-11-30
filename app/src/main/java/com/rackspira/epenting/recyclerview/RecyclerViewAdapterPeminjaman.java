package com.rackspira.epenting.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.MenuPilihan.UpdateActivity;
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
    RefreshHandler refreshHandler;

    public RecyclerViewAdapterPeminjaman(Context context, List<Hutang> hutangList, RefreshHandler refreshHandler) {
        this.context = context;
        this.hutangList = hutangList;
        this.refreshHandler = refreshHandler;
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
    public void onBindViewHolder(final RecyclerViewHolderPeminjaman holder, int position) {
        final Hutang hutang=hutangList.get(position);

        if (hutang.getStatus().toString().equals("Lunas")){
            holder.textViewStatus.setText(hutang.getStatus());
        }

        holder.textViewTanggalKembali.setText(hutang.getTgl_kembali());
        holder.textViewPemberiPinjaman.setText(hutang.getPemberiPinjaman());
        holder.textViewNominalPinjaman.setText(hutang.getNominal());
        if (hutang.getCicilan()!=null){
            holder.textViewDetailCicilan.setVisibility(View.VISIBLE);
            holder.textViewDetailCicilan.setText(hutang.getCicilan()+"x Cicilan dan harus dibayar setiap tanggal "+hutang.getTgl_bayar_cicilan());
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence[] charSequence={"Lunas", "Hapus"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                if (hutang.getCicilan()!=null){
                                    dbHutang.updateStatusCicilan(hutang.getId_hutang());
                                    holder.textViewStatus.setText("Lunas");
                                } else {
                                    dbHutang.updateStatusHutang(hutang.getId_hutang());
                                    holder.textViewStatus.setText("Lunas");
                                }
                                break;
                            case 1 :
                                if (hutang.getCicilan()!=null){
                                    dbHutang.deleteRowCicilan(hutang.getId_hutang());
                                    refreshHandler.onRefresh();
                                } else {
                                    dbHutang.deleteRowHutang(hutang.getId_hutang());
                                    refreshHandler.onRefresh();
                                }
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hutangList.size();
    }
}
