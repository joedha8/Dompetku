package com.rackspira.dompetku.recyclerview;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.MenuPilihan.UpdateActivity;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<DataMasuk> dataMasuks = new ArrayList<>();
    LayoutInflater inflater;
    DbHelper dbhelper;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    /*public RecyclerViewAdapter(Context context, List<DataMasuk> dataMasuks1) {
        this.context = context;
        this.dataMasuks = dataMasuks1;
        inflater = LayoutInflater.from(context);
        dbhelper=DbHelper.getInstance(context);
    }*/

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.list_view, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        /*final DataMasuk dataMasuk = dataMasuks.get(position);
        holder.keterangan.setText(dataMasuk.getKet());
        holder.pemasukkan_head.setText(dataMasuk.getStatus());
        holder.nominal.setText("Rp. " +dataMasuk.getBiaya()+",00");
        holder.tglMasuk.setText(dataMasuk.getTanggal());


        
        if( dataMasuk.getStatus().equals("Pemasukkan")){
            holder.gambar.setImageResource(R.drawable.ic_circle_plus_green);
        }else{
            holder.gambar.setImageResource(R.drawable.ic_circle_minus_red);
            holder.nominal.setBackgroundResource(R.drawable.shape_merah);
        }*/
        String[] pemasukan_head={"Make Up", "Make Up", "Make Up", "Make Up"};
        String[] keterangan={"Beli Lipstik", "Beli Foundation", "Beli Bedak Tabur", "Beli Bedak Padat"};
        String[] nominal={"Rp. 50.000", "Rp. 100.000", "Rp. 50.000", "Rp. 50.000"};
        String[] tanggal_masuk={"01-10-17", "01-10-17", "01-10-17", "01-10-17"};
        int[] gambar={R.drawable.ic_circle_minus_red, R.drawable.ic_circle_minus_red, R.drawable.ic_circle_minus_red, R.drawable.ic_circle_minus_red};

        holder.gambar.setImageResource(gambar[position]);
        holder.pemasukkan_head.setText(pemasukan_head[position]);
        holder.keterangan.setText(keterangan[position]);
        holder.nominal.setText(nominal[position]);
        holder.tglMasuk.setText(tanggal_masuk[position]);

        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence[] charSequence={"Lihat", "Update", "Hapus"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(context, UpdateActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1 :
                                dbhelper.deleteRow(dataMasuk.getKet(), dataMasuk.getBiaya(), dataMasuk.getTanggal());
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });*/
    }

    @Override
    public int getItemCount() {

        return 4;
    }
}