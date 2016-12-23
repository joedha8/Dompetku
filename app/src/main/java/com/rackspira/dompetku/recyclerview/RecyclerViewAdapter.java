package com.rackspira.dompetku.recyclerview;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.MainActivity;
import com.rackspira.dompetku.MenuPilihan.LihatActivity;
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

    public RecyclerViewAdapter(Context context, List<DataMasuk> dataMasuks1) {
        this.context = context;
        this.dataMasuks = dataMasuks1;
        inflater = LayoutInflater.from(context);
        dbhelper=DbHelper.getInstance(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.list_view, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final DataMasuk dataMasuk = dataMasuks.get(position);
        holder.keterangan.setText(dataMasuk.getKet());
        holder.pemasukkan_head.setText(dataMasuk.getStatus());
        holder.nominal.setText("Rp. " +dataMasuk.getBiaya()+",00");
        holder.tglMasuk.setText(dataMasuk.getTanggal());
        
        if( dataMasuk.getStatus().equals("Pemasukkan")){
            holder.gambar.setImageResource(R.drawable.ic_circle_plus_green);
        }else
            holder.gambar.setImageResource(R.drawable.ic_circle_minus_red);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence={"Lihat", "Update", "Hapus"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent=new Intent(context, LihatActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1 :
                                intent = new Intent(context, UpdateActivity.class);
                                context.startActivity(intent);
                                break;
                            case 2 :
                                dbhelper.deleteRow(dataMasuk.getKet());
                                intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
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

        return dataMasuks.size();
    }
}