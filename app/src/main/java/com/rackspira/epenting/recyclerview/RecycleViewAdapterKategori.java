package com.rackspira.epenting.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.database.Kategori;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewAdapterKategori extends RecyclerView.Adapter<RecycleViewHolderKategori> {
    Context context;
    LayoutInflater inflater;
    List<Kategori> kategoriList=new ArrayList<>();
    DbHelper dbHelper;

    public RecycleViewAdapterKategori(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList=kategoriList;
        inflater=LayoutInflater.from(context);
        dbHelper =DbHelper.getInstance(context);
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

        double batas = Double.parseDouble(kategori.getBatasPengeluaran());

        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String batasTampil= "Rp. " + decimalFormat.format(batas);

        if(batas == 0){
            holder.textViewKet.setText("Batas Pengeluaran tidak ada");
            holder.textViewBatas.setVisibility(View.GONE);
        }else {
            holder.textViewBatas.setVisibility(View.VISIBLE);
            holder.textViewKet.setText("Batas Pengeluaran");
            holder.textViewBatas.setText(batasTampil);
        }

        holder.textViewKategori.setText(kategori.getKategori());



    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }
}
