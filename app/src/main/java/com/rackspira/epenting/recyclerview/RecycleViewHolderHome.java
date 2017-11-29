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

public class RecycleViewHolderHome extends RecyclerView.ViewHolder {
    TextView textViewKeteranganHome, textViewNominalHome, textViewTotalPengeluaran;
    CardView cardViewHome;

    public RecycleViewHolderHome(View itemView) {
        super(itemView);

        textViewKeteranganHome=(TextView)itemView.findViewById(R.id.keteranganHome);
        textViewNominalHome=(TextView)itemView.findViewById(R.id.nominalHome);
        textViewTotalPengeluaran=(TextView)itemView.findViewById(R.id.total);
        cardViewHome=(CardView)itemView.findViewById(R.id.card_view_home);
    }
}
