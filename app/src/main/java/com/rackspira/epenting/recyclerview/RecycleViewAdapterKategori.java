package com.rackspira.epenting.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbKategori;
import com.rackspira.epenting.database.Kategori;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewAdapterKategori extends RecyclerView.Adapter<RecycleViewHolderKategori> {
    Context context;
    LayoutInflater inflater;
    List<Kategori> kategoriList=new ArrayList<>();
    DbKategori dbKategori;

    public RecycleViewAdapterKategori(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList=kategoriList;
        inflater=LayoutInflater.from(context);
        dbKategori=DbKategori.getInstance(context);
    }

    @Override
    public RecycleViewHolderKategori onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_kategori, parent, false);
        RecycleViewHolderKategori viewHolderKategori=new RecycleViewHolderKategori(view);

        return viewHolderKategori;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderKategori holder, int position) {
        Kategori kategori=kategoriList.get(position);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        holder.textViewKategori.setText(kategori.getKategori());
        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound(kategori.getKategori().substring(0,3), color1);
        holder.imgLogo.setImageDrawable(drawable1);
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }
}
