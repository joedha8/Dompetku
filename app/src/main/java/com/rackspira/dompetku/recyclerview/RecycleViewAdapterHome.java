package com.rackspira.dompetku.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.DetailActivity;
import com.rackspira.dompetku.R;

import java.util.zip.Inflater;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewAdapterHome extends RecyclerView.Adapter<RecycleViewHolderHome> {
    Context context;
    LayoutInflater inflater;

    public RecycleViewAdapterHome(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecycleViewHolderHome onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_view_home, parent, false);
        RecycleViewHolderHome viewHolderHome=new RecycleViewHolderHome(view);

        return viewHolderHome;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderHome holder, int position) {
        int[] imageArray={R.drawable.ic_circle_minus_red, R.drawable.ic_circle_plus_green, R.drawable.ic_circle_minus_red, R.drawable.ic_circle_minus_red};
        String[] keterangan={"Make Up", "Pemasukkan", "Makan", "Kampus"};
        String[] nominal={"Rp. 250.000", "Rp. 2.500.000", "Rp. 500.000", "Rp. 1.050.000"};

        //holder.imageViewHome.setImageResource(imageArray[position]);
        holder.textViewKeteranganHome.setText(keterangan[position]);
        holder.textViewNominalHome.setText(nominal[position]);

        holder.cardViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
