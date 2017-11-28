package com.rackspira.dompetku.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.DetailActivity;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.database.DbKategori;
import com.rackspira.dompetku.database.Kategori;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by kikiosha on 11/14/17.
 */

public class RecycleViewAdapterHome extends RecyclerView.Adapter<RecycleViewHolderHome> {
    Context context;
    List<Kategori> kategoriList = new ArrayList<>();
    LayoutInflater inflater;
    DbKategori dbKategori;
    DbHelper dbHelper;

    public RecycleViewAdapterHome(Context context, List<Kategori> kategoriList) {
        this.context = context;
        this.kategoriList=kategoriList;
        inflater=LayoutInflater.from(context);
        dbKategori=DbKategori.getInstance(context);
        dbHelper=DbHelper.getInstance(context);
    }

    @Override
    public RecycleViewHolderHome onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_view_home, parent, false);
        RecycleViewHolderHome viewHolderHome=new RecycleViewHolderHome(view);

        return viewHolderHome;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderHome holder, int position) {
        Kategori kategori=kategoriList.get(position);
        int[] pengeluaran=new int[dbKategori.getKategori().size()];

        for (int i = 0; i<dbKategori.getKategori().size(); i++){
            pengeluaran[i]=dbHelper.biayaPerKategori(kategori.getKategori());
        }
        //System.out.println("Kategorinya apa aja "+dbHelper.getPengeluaran().get(position).getKat());

        System.out.println("kategori "+kategori.getKategori());
        System.out.println("Biaya "+pengeluaran[position]);

        holder.textViewKeteranganHome.setText(""+kategori.getKategori());
        holder.textViewNominalHome.setText(""+pengeluaran[position]);

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
        return dbKategori.getKategori().size();
    }
}
