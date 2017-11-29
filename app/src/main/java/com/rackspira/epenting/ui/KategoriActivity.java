package com.rackspira.epenting.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbKategori;
import com.rackspira.epenting.database.Kategori;
import com.rackspira.epenting.recyclerview.RecycleViewAdapterKategori;

public class KategoriActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewAdapterKategori recycleViewAdapterKategori;
    DbKategori dbKategori;
    Button buttonInsertKategori;
    EditText editTextInsetKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewKategori);
        dbKategori=DbKategori.getInstance(getApplicationContext());
        buttonInsertKategori=(Button)findViewById(R.id.button_insert_kategori);
        editTextInsetKategori=(EditText)findViewById(R.id.edittext_insert_kategori);

        buttonInsertKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kategori kategori=new Kategori();

                kategori.setKategori(editTextInsetKategori.getText().toString());

                if (!editTextInsetKategori.getText().toString().isEmpty()){
                    dbKategori.insertKategori(kategori);
                    refreshList();
                    editTextInsetKategori.setText("");
                }
            }
        });

        refreshList();
    }

    public void refreshList(){
        recycleViewAdapterKategori=new RecycleViewAdapterKategori(this, dbKategori.getKategori());
        recyclerView.setAdapter(recycleViewAdapterKategori);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleViewAdapterKategori.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(KategoriActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
