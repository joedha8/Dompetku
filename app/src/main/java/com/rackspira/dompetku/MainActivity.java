package com.rackspira.dompetku;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rackspira.dompetku.adapterRecyclerView.RecyclerViewAdapter;
import com.rackspira.dompetku.database.DbHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rview;
    DbHelper dbHelper;
    RecyclerViewAdapter adapter;
    TextView pemasukkan, pengeluaran;
    CardView cardView;
    String masuk, keluar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, MasukActivity.class);
                startActivity(intent);
            }
        });

        pemasukkan=(TextView)findViewById(R.id.pemasukkan);
        pengeluaran=(TextView)findViewById(R.id.pengeluaran);
        cardView=(CardView)findViewById(R.id.card_view);
        dbHelper = DbHelper.getInstance(getApplicationContext());

        dbHelper.jumMasuk();
        dbHelper.jumKeluar();
        uangFormat();



        refreshList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void uangFormat(){
        String stringMasuk=""+dbHelper.jumMasuk;
        String stringKeluar=""+dbHelper.jumKeluar;
        double pemasukkan=Double.parseDouble(stringMasuk);
        double pengeluaran=Double.parseDouble(stringKeluar);
        DecimalFormat decimalFormat=(DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols=new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String hasilMasuk="Rp. "+decimalFormat.format(pemasukkan);
        String hasolKeluar="Rp. "+decimalFormat.format(pengeluaran);
        masuk=hasilMasuk;
        keluar=hasolKeluar;
    }

    public void refreshList(){
        rview = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, dbHelper.getMasuk());
        rview.setAdapter(adapter);
        rview.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        pemasukkan.setText(masuk);
        pengeluaran.setText(keluar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent=new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_pemasukkan) {
            Intent intent=new Intent(MainActivity.this, Pemasukkan.class);
            startActivity(intent);
        } else if (id == R.id.nav_pengeluaran) {
            Intent intent=new Intent(MainActivity.this, Pengeluaran.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
