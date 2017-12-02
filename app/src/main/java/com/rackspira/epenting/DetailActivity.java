package com.rackspira.epenting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.rackspira.epenting.R;
import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapter;
import com.rackspira.epenting.ui.MainActivity;

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
        getSupportActionBar().setTitle(getIntent().getStringExtra("kategori"));

        refreshList();
    }

    public void refreshList() {
        Intent intent=getIntent();
        String kategori=intent.getStringExtra("kategori");
        dbHelper = DbHelper.getInstance(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewDetail);
        adapter=new RecyclerViewAdapter(this, dbHelper.sortByKategori(kategori),this,dbHelper);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
