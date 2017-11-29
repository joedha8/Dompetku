package com.rackspira.epenting.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pengeluaran extends AppCompatActivity {
    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    String dateAwal, dateAkhir;
    EditText tglAwal, tglAkhir;
    Button filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHelper = DbHelper.getInstance(getApplicationContext());
        tglAwal=(EditText)findViewById(R.id.tgl_awal_akhir);
        tglAkhir=(EditText)findViewById(R.id.tgl_akhir_akhir);
        filter=(Button)findViewById(R.id.btn_filter_keluar);

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        String tanggalAwal=sdf.format( new Date() );
        tglAwal.setText(tanggalAwal);
        dateAwal=tanggalAwal;

        SimpleDateFormat sdf1 = new SimpleDateFormat( "dd-MM-yyyy" );
        String tanggalAkhir=sdf1.format( new Date() );
        tglAkhir.setText(tanggalAkhir);
        dateAkhir=tanggalAkhir;

        tglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int tahun=calendar.get(Calendar.YEAR);
                int bulan=calendar.get(Calendar.MONTH);
                int hari=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Pengeluaran.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date=day+"-"+(month+1)+"-"+year;
                        tglAwal.setText(date);
                        dateAwal=date;
                    }
                }, tahun, bulan, hari);
                datePickerDialog.show();
            }
        });

        tglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int tahun=calendar.get(Calendar.YEAR);
                int bulan=calendar.get(Calendar.MONTH);
                int hari=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Pengeluaran.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date=day+"-"+(month+1)+"-"+year;
                        tglAkhir.setText(date);
                        dateAkhir=date;
                    }
                }, tahun, bulan, hari);
                datePickerDialog.show();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshRview();
            }
        });
    }

    public void refreshRview(){
        rview = (RecyclerView)findViewById(R.id.recyclerKeluar);
        adapter = new RecyclerViewAdapter(this, dbHelper.getPengeluaran(dateAwal, dateAkhir));
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
            Intent intent=new Intent(Pengeluaran.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
