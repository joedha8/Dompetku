package com.rackspira.dompetku.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.R;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewAdapterKategori extends RecyclerView.Adapter<RecycleViewHolderKategori> {
    Context context;
    LayoutInflater inflater;

    public RecycleViewAdapterKategori(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecycleViewHolderKategori onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_kategori, parent, false);
        RecycleViewHolderKategori viewHolderKategori=new RecycleViewHolderKategori(view);

        return viewHolderKategori;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderKategori holder, int position) {
        String[] kategoriArray={"Make Up", "Makan", "Kampus"};

        holder.textViewKategori.setText(kategoriArray[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
