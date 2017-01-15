package com.rackspira.dompetku.recyclerview;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rackspira.dompetku.R;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView keterangan,nominal,pemasukkan_head, tglMasuk;
    ImageView gambar;
    CardView cardView;

    public RecyclerViewHolder(View itemView){
        super(itemView);
        keterangan = (TextView)itemView.findViewById(R.id.keterangan);
        nominal = (TextView)itemView.findViewById(R.id.nominal);
        gambar = (ImageView)itemView.findViewById(R.id.gambar);
        pemasukkan_head = (TextView)itemView.findViewById(R.id.pemasukkan_head);
        tglMasuk = (TextView)itemView.findViewById(R.id.tglMasuk);
        cardView = (CardView)itemView.findViewById(R.id.card_view);
    }
}