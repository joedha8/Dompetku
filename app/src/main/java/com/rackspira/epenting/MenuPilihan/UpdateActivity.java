package com.rackspira.epenting.MenuPilihan;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DataMasuk;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.model.GlobalDataMasuk;
import com.rackspira.epenting.ui.MainActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView ket, nom, tgl;
    private EditText keterangan, nominal, tanggal;
    private Button save, cancel;
    String iniTanggal;
    DbHelper dbHelper;
    DataMasuk dataMasuk;
    String nominalAkhir;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Data");

        ket = (TextView) findViewById(R.id.ket_edit_view);
        nom = (TextView) findViewById(R.id.nom_edit_view);
        tgl = (TextView) findViewById(R.id.tgl_edit_view);
        keterangan = (EditText) findViewById(R.id.ket_edit);
        nominal = (EditText) findViewById(R.id.nom_edit);
        tanggal = (EditText) findViewById(R.id.tgl_edit);
        save = (Button) findViewById(R.id.btn_simpan);
        cancel = (Button) findViewById(R.id.btn_cancel);
        dbHelper = DbHelper.getInstance(getApplicationContext());
        dataMasuk = new DataMasuk();

        uangFormat();

        ket.setText("" + GlobalDataMasuk.getDataMasuk().getKet());
        nom.setText(nominalAkhir);
        tgl.setText("" + GlobalDataMasuk.getDataMasuk().getTanggal());
        keterangan.setText("" + GlobalDataMasuk.getDataMasuk().getKet());
        nominal.setText("" + GlobalDataMasuk.getDataMasuk().getBiaya());
        tanggal.setText("" + GlobalDataMasuk.getDataMasuk().getTanggal());

//        keterangan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                keterangan.setText("");
//            }
//        });
//
//        nominal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nominal.setText("");
//            }
//        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        UpdateActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show(getFragmentManager(), "DatePicker Dialog");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DataMasuk dataMasuk=new DataMasuk();
                dataMasuk.setKet(keterangan.getText().toString());
                dataMasuk.setBiaya(nominal.getText().toString());
                dataMasuk.setTanggal(tanggal.getText().toString());
                dataMasuk.setId(GlobalDataMasuk.getDataMasuk().getId());

                if (!TextUtils.isEmpty(nominal.getText().toString()) &&
                        !TextUtils.isEmpty(keterangan.getText().toString()) &&
                        !TextUtils.isEmpty(tanggal.getText().toString())){
                    dbHelper.updateData(dataMasuk);
                    Intent intent=new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar.make(view,"Data tidak boleh kosong",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void uangFormat(){
        String stringNominal=""+GlobalDataMasuk.getDataMasuk().getBiaya();
        double nominal=Double.parseDouble(stringNominal);
        DecimalFormat decimalFormat=(DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols=new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        String hasilNominal="Rp. "+decimalFormat.format(nominal);
        nominalAkhir=hasilNominal;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tgl = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
        tanggal.setText(tgl);
        iniTanggal = tgl;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
