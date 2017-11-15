package com.rackspira.dompetku.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rackspira.dompetku.R;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewHolderHome extends RecyclerView.ViewHolder {
    TextView textViewKeteranganHome, textViewNominalHome;
    ImageView imageViewHome;
    CardView cardViewHome;

    public RecycleViewHolderHome(View itemView) {
        super(itemView);

        textViewKeteranganHome=(TextView)itemView.findViewById(R.id.keteranganHome);
        textViewNominalHome=(TextView)itemView.findViewById(R.id.nominalHome);
        //imageViewHome=(ImageView)itemView.findViewById(R.id.gambarHome);
        cardViewHome=(CardView)itemView.findViewById(R.id.card_view_home);
    }
}
