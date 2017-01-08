package com.rackspira.dompetku;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Diagram_keungan extends AppCompatActivity {

    PieChart pie ;
    private String[] xData = {"data1,data2,data3"};
    private float[] yData = {50.5f,40.3f,60.3f};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram_keungan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Diagram Keuangan");

        pie = (PieChart)findViewById(R.id.pieChart);

        pie.getDescription().setText("Pengeluaran Bulan ini");
        pie.setRotationEnabled(true);
        pie.setHoleRadius(30);
        pie.setDrawEntryLabels(true);
        pie.setCenterText("Diagram Keuangan");
        pie.setCenterTextSize(12);
        pie.setTransparentCircleAlpha(0);
        
        addData(pie);
    }

    private void addData(PieChart pieMasuk) {

        ArrayList<PieEntry> yEntri = new ArrayList<>();
        ArrayList<String> xEntri = new ArrayList<>();

        //penambahan data
        for (int i =0; i<yData.length;i++){
            yEntri.add(new PieEntry(yData[i],i));
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

        warna.add(Color.RED);
        warna.add(Color.GREEN);
        warna.add(Color.BLUE);

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
