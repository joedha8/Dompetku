package com.rackspira.epenting.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rackspira.epenting.R;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewHolderKategori extends RecyclerView.ViewHolder {
    TextView textViewKategori;
    CardView cardViewKategori;
    TextView textViewBatas;
    TextView textViewKet;

    public RecycleViewHolderKategori(View itemView) {
        super(itemView);

        textViewKategori=(TextView)itemView.findViewById(R.id.textViewKategori);
        cardViewKategori=(CardView)itemView.findViewById(R.id.card_viewKategori);
        textViewBatas = (TextView)itemView.findViewById(R.id.btsLimited);
        textViewKet = (TextView)itemView.findViewById(R.id.textKet);
    }
}
