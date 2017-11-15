package com.rackspira.dompetku.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rackspira.dompetku.R;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewHolderKategori extends RecyclerView.ViewHolder {
    TextView textViewKategori;
    CardView cardViewKategori;

    public RecycleViewHolderKategori(View itemView) {
        super(itemView);

        textViewKategori=(TextView)itemView.findViewById(R.id.textViewKategori);
        cardViewKategori=(CardView)itemView.findViewById(R.id.card_viewKategori);
    }
}
