package com.rackspira.dompetku.interfaceClass;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rackspira.dompetku.R;
import com.rackspira.dompetku.adapterRecyclerView.RecyclerViewAdapter;
import com.rackspira.dompetku.database.DbHelper;

/**
 * Created by YUDHA on 25/12/2016.
 */

public class RefreshMain extends AppCompatActivity implements Refresh {
    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    @Override
    public void refreshList() {
        rview = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, dbHelper.getMasuk());
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
    }
}
