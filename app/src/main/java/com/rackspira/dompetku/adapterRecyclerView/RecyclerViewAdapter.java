package com.rackspira.dompetku.adapterRecyclerView;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.dompetku.MenuPilihan.RefreshHandler;
import com.rackspira.dompetku.MenuPilihan.UpdateActivity;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.model.GlobalDataMasuk;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<DataMasuk> dataMasuks = new ArrayList<>();
    LayoutInflater inflater;
    DbHelper dbhelper;
    RefreshHandler refreshHandler;

    public RecyclerViewAdapter(Context context, List<DataMasuk> dataMasuks1, RefreshHandler refreshHandler) {
        this.context = context;
        this.dataMasuks = dataMasuks1;
        this.refreshHandler = refreshHandler;
        inflater = LayoutInflater.from(context);
        dbhelper=DbHelper.getInstance(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.list_view, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(convertView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final DataMasuk dataMasuk = dataMasuks.get(position);

        System.out.println(dataMasuk.getBiaya());
        double biaya=Double.parseDouble(dataMasuk.getBiaya());

        DecimalFormat decimalFormat=(DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols=new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String hasilBiaya="Rp. "+decimalFormat.format(biaya);

        holder.keterangan.setText(dataMasuk.getKet());
        holder.pemasukkan_head.setText(dataMasuk.getStatus());
        holder.nominal.setText(hasilBiaya);
        holder.tglMasuk.setText(dataMasuk.getTanggal());
        
        if( dataMasuk.getStatus().equals("Pemasukkan")){
            holder.gambar.setImageResource(R.drawable.ic_circle_plus_green);
        }else{
            holder.gambar.setImageResource(R.drawable.ic_circle_minus_red);
            holder.nominal.setBackgroundResource(R.drawable.shape_merah);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence={"Update", "Hapus"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                GlobalDataMasuk.setDataMasuk(dataMasuks.get(position));
                                Intent intent = new Intent(context, UpdateActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1 :
                                dbhelper.deleteRow(dataMasuk.getKet(), dataMasuk.getBiaya(), dataMasuk.getTanggal());
                                dataMasuks.remove(dataMasuks.get(position));
                                refreshHandler.onRefresh();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataMasuks.size();
    }
}