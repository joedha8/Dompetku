package com.rackspira.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.recyclerview.RecyclerViewAdapter;

public class Pemasukkan extends AppCompatActivity {
    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukkan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHelper = DbHelper.getInstance(getApplicationContext());


        rview = (RecyclerView)findViewById(R.id.recyclerMasuk);
        adapter = new RecyclerViewAdapter(this, dbHelper.getPemasukkan());
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(Pemasukkan.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
