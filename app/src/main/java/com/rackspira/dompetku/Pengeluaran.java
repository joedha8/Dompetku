package com.rackspira.dompetku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.recyclerview.RecyclerViewAdapter;

public class Pengeluaran extends AppCompatActivity {
    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    EditText tgl_awal_keluar,tgl_akhir_keluar;
    Button btn_filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);
        dbHelper = DbHelper.getInstance(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rview = (RecyclerView)findViewById(R.id.recyclerKeluar);
        adapter = new RecyclerViewAdapter(this, dbHelper.getPengeluaran());
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
        tgl_akhir_keluar = (EditText)findViewById(R.id.tgl_akhir_keluar);
        tgl_awal_keluar = (EditText)findViewById(R.id.tgl_awal_keluar);
        btn_filter =(Button)findViewById(R.id.btn_filter_keluar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(Pengeluaran.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
