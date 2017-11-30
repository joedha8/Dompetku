package com.rackspira.epenting.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.epenting.DetailActivity;
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

public class RecycleViewAdapterHome extends RecyclerView.Adapter<RecycleViewHolderHome> {
    Context context;
    List<Kategori> kategoriList = new ArrayList<>();
    LayoutInflater inflater;
    DbHelper dbHelper;

    public RecycleViewAdapterHome(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList=kategoriList;
        inflater=LayoutInflater.from(context);
        dbHelper=DbHelper.getInstance(context);
    }

    @Override
    public RecycleViewHolderHome onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_view_home, parent, false);
        RecycleViewHolderHome viewHolderHome=new RecycleViewHolderHome(view);

        return viewHolderHome;
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolderHome holder, int position) {
        Kategori kategori=kategoriList.get(position);
        int[] pengeluaran=new int[dbHelper.getKategori().size()];

        for (int i = 0; i<dbHelper.getKategori().size(); i++){
            pengeluaran[i]=dbHelper.biayaPerKategori(kategori.getKategori());
        }

        System.out.println("kategori "+kategori.getKategori());
        System.out.println("Biaya "+pengeluaran[position]);
        double batas = Double.parseDouble(kategori.getBatasPengeluaran());
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String biayaTampil= "Rp. " + decimalFormat.format(pengeluaran[position]);
        String batasTampil= "Rp. " + decimalFormat.format(batas);

        if (pengeluaran[position]==0){
            holder.textViewKeteranganHome.setText(""+kategori.getKategori());
            holder.textViewTotalPengeluaran.setText("Belum ada data pada kategori ini");
            holder.textViewNominalHome.setVisibility(View.GONE);
            holder.cardViewHome.setClickable(false);
        } else {


            holder.textViewKeteranganHome.setText(""+kategori.getKategori());
            holder.textViewNominalHome.setText(""+biayaTampil);
            holder.cardViewHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, DetailActivity.class);
                    intent.putExtra("kategori", holder.textViewKeteranganHome.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
        Log.d("biayaTampil",batasTampil);
        Log.d("batas","" + batas);
        if(batas == 0){
            holder.textViewKetHome.setText("Batas Pengeluaran tidak ada");
            holder.textViewBatasHome.setVisibility(View.GONE);
        }else {
            holder.textViewBatasHome.setVisibility(View.VISIBLE);
            holder.textViewKetHome.setText("Batas Pengeluaran");
            holder.textViewBatasHome.setText(batasTampil);
        }
    }

    @Override
    public int getItemCount() {
        return dbHelper.getKategori().size();
    }
}
