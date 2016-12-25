package com.rackspira.dompetku.MenuPilihan;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rackspira.dompetku.MainActivity;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;
import com.rackspira.dompetku.model.GlobalDataMasuk;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView ket, nom, tgl;
    private EditText keterangan, nominal, tanggal;
    private Button save, cancel;
    String iniTanggal;
    DbHelper dbHelper;
    DataMasuk dataMasuk;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

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
        //String biaya= NumberFormat.getInstance().format(Integer.parseInt(dataMasuk.getBiaya()));

        ket.setText("" + GlobalDataMasuk.getDataMasuk().getKet());
        nom.setText("" + GlobalDataMasuk.getDataMasuk().getBiaya());
        tgl.setText("" + GlobalDataMasuk.getDataMasuk().getTanggal());

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

                if (!keterangan.getText().toString().isEmpty()){
                    dataMasuk.setKet(keterangan.getText().toString());
                }
                else {
                    dataMasuk.setKet("");
                }
                if (!nominal.getText().toString().isEmpty()){
                    dataMasuk.setBiaya(nominal.getText().toString());
                }
                else {
                    dataMasuk.setBiaya("");
                }
                if (!tanggal.getText().toString().isEmpty()){
                    dataMasuk.setTanggal(tanggal.getText().toString());
                }
                else {
                    dataMasuk.setTanggal("");
                }

                dataMasuk.setTanggal(iniTanggal);

                dbHelper.updateData(dataMasuk);

                Intent intent=new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tgl = dayOfMonth + "-" + monthOfYear + "-" + year;
        tanggal.setText(tgl);
        iniTanggal = tgl;
    }
}
