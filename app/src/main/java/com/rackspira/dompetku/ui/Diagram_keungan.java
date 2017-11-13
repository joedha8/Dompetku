package com.rackspira.dompetku.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.ArrayList;

public class Diagram_keungan extends AppCompatActivity {
    PieChart pie, pieChartFilter;
    DbHelper dbHelper;
    private String[] xData, xDataFilter;
    private int[] yData, yDataFilter;
    private Button btnBulan;
    private TextView teksBulan;
    private int bulan, tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram_keungan);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diagram Keuangan");

        pie = (PieChart)findViewById(R.id.pieChart);
        pieChartFilter=(PieChart)findViewById(R.id.pieChartFilter);
        btnBulan=(Button)findViewById(R.id.bulan);
        teksBulan=(TextView)findViewById(R.id.bulanFilterView);

        pie.getDescription().setText("Pengeluaran Keseluruhan");
        pie.setRotationEnabled(true);
        pie.setHoleRadius(30);
        pie.setDrawEntryLabels(true);
        pie.setCenterText("Diagram Keuangan");
        pie.setCenterTextSize(12);
        pie.setTransparentCircleAlpha(40);
        pie.animateXY(3000 ,3000);
        
        addData(pie);

        btnBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RackMonthPicker(Diagram_keungan.this)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                teksBulan.setText(""+monthLabel);
                                bulan=month;
                                tahun=year;

                                pieChartFilter.getDescription().setText("Pengeluaran Bulan ini");
                                pieChartFilter.setRotationEnabled(true);
                                pieChartFilter.setHoleRadius(30);
                                pieChartFilter.setDrawEntryLabels(true);
                                pieChartFilter.setCenterText("Diagram Keuangan");
                                pieChartFilter.setCenterTextSize(8);
                                pieChartFilter.setTransparentCircleAlpha(40);
                                pieChartFilter.animateXY(3000 ,3000);

                                pieChartBulan(pieChartFilter);
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void addData(PieChart pieMasuk) {
        dbHelper=DbHelper.getInstance(getApplicationContext());
        dbHelper.jumMasuk();
        dbHelper.jumKeluar();
        String masukString = ""+dbHelper.jumMasuk;
        String keluarString = ""+dbHelper.jumKeluar;
        xData=new String[]{"Pemasukkan", "Pengeluaran"};
        yData=new int[]{Integer.parseInt(masukString), Integer.parseInt(keluarString)};

        ArrayList<PieEntry> yEntri = new ArrayList<>();
        ArrayList<String> xEntri = new ArrayList<>();

        //penambahan data
        for (int i =0; i<yData.length;i++){
            yEntri.add(new PieEntry(yData[i],xData[i]));
        }

        for (int i =0;i<xData.length;i++){
            xEntri.add(xData[i]);
        }

        //set Data
        PieDataSet pieDataSet = new PieDataSet(yEntri,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(14);

        //setting warna

        ArrayList<Integer> warna = new ArrayList<>();

        warna.add(Color.GREEN);
        warna.add(Color.RED);

        pieDataSet.setColors(warna);

        //menambahkan legend

        Legend legend = pieMasuk.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //buat Objek pie Data

        PieData pieData = new PieData(pieDataSet);
        pieMasuk.setData(pieData);
        pieMasuk.invalidate();
    }

    private void pieChartBulan(PieChart pieMasuk){
        dbHelper=DbHelper.getInstance(getApplicationContext());
        dbHelper.jumMasukBulanan(bulan,tahun);
        dbHelper.jumKeluarBulanan(bulan,tahun);
        String masukString = ""+dbHelper.jumMasukBulannan;
        String keluarString = ""+dbHelper.jumKeluarBulanan;
        xDataFilter=new String[]{"Pemasukkan", "Pengeluaran"};
        yDataFilter=new int[]{Integer.parseInt(masukString), Integer.parseInt(keluarString)};

        ArrayList<PieEntry> yEntriFilter = new ArrayList<>();
        ArrayList<String> xEntriFilter = new ArrayList<>();

        //penambahan data
        for (int i =0; i<yDataFilter.length;i++){
            yEntriFilter.add(new PieEntry(yDataFilter[i],xDataFilter[i]));
        }

        for (int i =0;i<xDataFilter.length;i++){
            xEntriFilter.add(xDataFilter[i]);
        }

        //set Data
        PieDataSet pieDataSetFilter = new PieDataSet(yEntriFilter,"");
        pieDataSetFilter.setSliceSpace(2);
        pieDataSetFilter.setValueTextSize(14);

        //setting warna

        ArrayList<Integer> warna = new ArrayList<>();

        warna.add(Color.GREEN);
        warna.add(Color.RED);

        pieDataSetFilter.setColors(warna);

        //menambahkan legend

        Legend legend = pieMasuk.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //buat Objek pie Data

        PieData pieData = new PieData(pieDataSetFilter);
        pieMasuk.setData(pieData);
        pieMasuk.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(Diagram_keungan.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}