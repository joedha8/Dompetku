package com.rackspira.dompetku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rackspira.dompetku.MenuPilihan.RefreshHandler;
import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.recyclerview.RecycleViewAdapterHome;
import com.rackspira.dompetku.recyclerview.RecycleViewAdapterKategori;
import com.rackspira.dompetku.recyclerview.RecyclerViewAdapter;
import com.rackspira.dompetku.ui.MainActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailActivity extends AppCompatActivity implements RefreshHandler {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        refreshList();
    }

    public void refreshList() {
        dbHelper = DbHelper.getInstance(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewDetail);
        adapter=new RecyclerViewAdapter(this, dbHelper.getMasuk(), this,dbHelper);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        refreshList();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
