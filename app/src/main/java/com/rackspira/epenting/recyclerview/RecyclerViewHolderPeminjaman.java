package com.rackspira.epenting.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rackspira.epenting.R;

/**
 * Created by kikiosha on 11/30/17.
 */

public class RecyclerViewHolderPeminjaman extends RecyclerView.ViewHolder {
    TextView textViewTanggalKembali, textViewPemberiPinjaman, textViewNominalPinjaman, textViewDetailCicilan, textViewStatus;
    CardView cardView;

    public RecyclerViewHolderPeminjaman(View itemView) {
        super(itemView);
        textViewStatus=(TextView)itemView.findViewById(R.id.status);
        textViewTanggalKembali=(TextView)itemView.findViewById(R.id.tglKembali);
        textViewPemberiPinjaman=(TextView)itemView.findViewById(R.id.pemberiPinjaman);
        textViewNominalPinjaman=(TextView)itemView.findViewById(R.id.nominalPinjaman);
        textViewDetailCicilan=(TextView)itemView.findViewById(R.id.cicilan_detail);
        cardView=(CardView)itemView.findViewById(R.id.card_view_hutang);
    }
}
