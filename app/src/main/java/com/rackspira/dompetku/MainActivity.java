package com.rackspira.dompetku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rackspira.dompetku.MenuPilihan.RefreshHandler;
import com.rackspira.dompetku.adapterRecyclerView.RecyclerViewAdapter;
import com.rackspira.dompetku.database.DbHelper;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RefreshHandler {

    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    TextView pemasukkanText, pengeluaranText;
    CardView cardView;
    String masuk, keluar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MasukActivity.class);
                startActivity(intent);
            }
        });

        pemasukkanText = (TextView) findViewById(R.id.pemasukkan);
        pengeluaranText = (TextView) findViewById(R.id.pengeluaran);
        cardView = (CardView) findViewById(R.id.card_view);
        dbHelper = DbHelper.getInstance(getApplicationContext());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        uangFormat();
        refreshList();
    }

    public void uangFormat() {
        dbHelper.jumMasuk();
        dbHelper.jumKeluar();
        String stringMasuk = "" + dbHelper.jumMasuk;
        String stringKeluar = "" + dbHelper.jumKeluar;
        double pemasukkan = Double.parseDouble(stringMasuk);
        double pengeluaran = Double.parseDouble(stringKeluar);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String hasilMasuk = "Rp. " + decimalFormat.format(pemasukkan);
        String hasolKeluar = "Rp. " + decimalFormat.format(pengeluaran);
        masuk = hasilMasuk;
        keluar = hasolKeluar;
        pemasukkanText.setText(masuk);
        pengeluaranText.setText(keluar);
    }

    public void refreshList() {
        rview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, dbHelper.getMasuk(), this);
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Ingin keluar dari Dompetku??")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Tidak",null).show();

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pemasukkan) {
            Intent intent = new Intent(MainActivity.this, Pemasukkan.class);
            startActivity(intent);
        } else if (id == R.id.nav_pengeluaran) {
            Intent intent = new Intent(MainActivity.this, Pengeluaran.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Dompetku Rack Edan Rack Spira";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_tentang) {
            Intent intent = new Intent(MainActivity.this, Tentang.class);
            startActivity(intent);
        }
        else if(id == R.id.chart){
            Intent intent = new Intent(MainActivity.this,Diagram_keungan.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        uangFormat();
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            AlertDialog.Builder alertDialogBuilder = null;
//            if (alertDialogBuilder == null) {
//                alertDialogBuilder = new AlertDialog.Builder(this);
//            }
//            alertDialogBuilder.setTitle("Keluar Dari Aplikasi Dompetku?");
//            alertDialogBuilder
//                    .setMessage("")
//                    .setCancelable(false)
//                    .setPositiveButton("YA",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    finish();
//                                }
//                            })
//
//                    .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            }).create().show();
//        }
//        return false;
//    }
}
