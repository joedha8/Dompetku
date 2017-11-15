package com.rackspira.dompetku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rackspira.dompetku.recyclerview.RecycleViewAdapterKategori;

public class KategoriActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewAdapterKategori recycleViewAdapterKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewKategori);
        recycleViewAdapterKategori=new RecycleViewAdapterKategori(this);
        recyclerView.setAdapter(recycleViewAdapterKategori);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
