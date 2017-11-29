package com.rackspira.epenting.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.database.DbKategori;
import com.rackspira.epenting.recyclerview.RecycleViewAdapterHome;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapter;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RefreshHandler {

    RecyclerView rview;
    RecyclerView rviewTerakhri;
    DbHelper dbHelper;
    DbKategori dbKategori;
    Button btnPilihan;

    RecyclerViewAdapter adapter;
    TextView pemasukkanText, pengeluaranText, saldoText, textKet;
    CardView cardView;
    String masuk, keluar, saldo;
    com.github.clans.fab.FloatingActionButton fabTambahKategori;
    com.github.clans.fab.FloatingActionButton fabTambahData;

    RecycleViewAdapterHome adapterHome;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        fabTambahKategori = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.menuTambahKategori);
        fabTambahData = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.menuTambahData);
        btnPilihan = (Button)findViewById(R.id.btnUrutan);
        textKet = (TextView)findViewById(R.id.textKet);


        fabTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        });

        fabTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MasukActivity.class);
                startActivity(intent);
            }
        });

        pemasukkanText = (TextView) findViewById(R.id.pemasukkan);
        pengeluaranText = (TextView) findViewById(R.id.pengeluaran);
        saldoText = (TextView) findViewById(R.id.saldo);
        cardView = (CardView) findViewById(R.id.card_view);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        dbKategori = DbKategori.getInstance(getApplicationContext());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (dbKategori.getKategori().size() == 0){
            textKet.setVisibility(View.VISIBLE);
        }else {
            textKet.setVisibility(View.GONE);
        }

        uangFormat();
        refreshList();
        refreshList2();
        btnPilihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] charSequence={"Semua Data", "Kategori"};
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                if (dbHelper.getMasuk().size() == 0){
                                    textKet.setVisibility(View.VISIBLE);
                                    rview.setVisibility(View.GONE);
                                    btnPilihan.setText("Semua Data");
                                }else {
                                    textKet.setVisibility(View.GONE);
                                    rview.setVisibility(View.GONE);
                                    rviewTerakhri.setVisibility(View.VISIBLE);
                                    btnPilihan.setText("Semua Data");
                                }
                                break;
                            case 1 :
                                if (dbKategori.getKategori().size() == 0){
                                    textKet.setVisibility(View.VISIBLE);
                                    rviewTerakhri.setVisibility(View.GONE);
                                    btnPilihan.setText("Kategori");
                                }else {
                                    textKet.setVisibility(View.GONE);
                                    rview.setVisibility(View.VISIBLE);
                                    rviewTerakhri.setVisibility(View.GONE);
                                    btnPilihan.setText("Kategori");
                                }
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    private void refreshList2() {
        rviewTerakhri = (RecyclerView)findViewById(R.id.recyclerviewTerakhir);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        adapter=new RecyclerViewAdapter(this, dbHelper.getMasuk(), this,dbHelper);
        rviewTerakhri.setHasFixedSize(true);
        rviewTerakhri.setAdapter(adapter);
        rviewTerakhri.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    public void uangFormat() {
        dbHelper.jumMasuk();
        dbHelper.jumKeluar();
        String stringMasuk = "" + dbHelper.jumMasuk;
        String stringKeluar = "" + dbHelper.jumKeluar;
        double pemasukkan = Double.parseDouble(stringMasuk);
        double pengeluaran = Double.parseDouble(stringKeluar);
        double saldonya=pemasukkan-pengeluaran;
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String hasilMasuk = "Rp. " + decimalFormat.format(pemasukkan);
        String hasolKeluar = "Rp. " + decimalFormat.format(pengeluaran);
        String hasilSaldo = "Rp. "+decimalFormat.format(saldonya);
        masuk = hasilMasuk;
        keluar = hasolKeluar;
        saldo=hasilSaldo;
        pemasukkanText.setText(masuk);
        pengeluaranText.setText(keluar);
        saldoText.setText(saldo);
    }

    public void refreshList() {
        rview = (RecyclerView) findViewById(R.id.recyclerview);
        //adapter = new RecyclerViewAdapter(this, dbHelper.getMasuk(), this);
        adapterHome=new RecycleViewAdapterHome(MainActivity.this, dbKategori.getKategori());
        rview.setAdapter(adapterHome);
        rview.setLayoutManager(new LinearLayoutManager(this));
        adapterHome.notifyDataSetChanged();
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
                    .setNegativeButton("Tidak", null).show();

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
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Download and use this application https://play.google.com/store/apps/details?id=com.rackspira.dompetku";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_tentang) {
            Intent intent = new Intent(MainActivity.this, Tentang.class);
            startActivity(intent);
        } else if (id == R.id.chart) {
            Intent intent = new Intent(MainActivity.this, Diagram_keungan.class);
            startActivity(intent);
        } else if (id == R.id.export) {
            Intent intent = new Intent(MainActivity.this, ExportActivity.class);
            startActivity(intent);
        }else if(id == R.id.setting){
            Intent intent = new Intent(MainActivity.this,Setting.class);
            startActivity(intent);
        }else if (id == R.id.backup_restore){
            Intent intent = new Intent(MainActivity.this, BackupRestoreActivity.class);
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
}
