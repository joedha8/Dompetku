package com.rackspira.dompetku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.recyclerview.RecyclerViewAdapter;

public class Pengeluaran extends AppCompatActivity {
    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);
        dbHelper = DbHelper.getInstance(getApplicationContext());


        rview = (RecyclerView)findViewById(R.id.recyclerKeluar);
        adapter = new RecyclerViewAdapter(this, dbHelper.getPengeluaran());
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
    }
}
