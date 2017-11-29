package com.rackspira.epenting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapter;

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
