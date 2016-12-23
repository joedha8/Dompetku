package com.rackspira.dompetku;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class MasukActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    SQLiteDatabase db;
    DbHelper dbHelper;
    private EditText edtKet, edtNom;
    private Button btnSave;
    private RadioGroup radioStatus;
    private RadioButton radioStatusButton;
    private EditText tanggal;
    private String tglnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        edtKet=(EditText)findViewById(R.id.ket);
        edtNom=(EditText)findViewById(R.id.nom);
        btnSave=(Button)findViewById(R.id.save);
        tanggal=(EditText)findViewById(R.id.tgl);
        radioStatus=(RadioGroup)findViewById(R.id.stat);
        dbHelper=DbHelper.getInstance(getApplicationContext());

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                DatePickerDialog datePickerDialog= DatePickerDialog.newInstance(
                        MasukActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show(getFragmentManager(), "DatePicker Dialog");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DataMasuk dataMasuk=new DataMasuk();

                if(!edtKet.getText().toString().isEmpty()){
                    dataMasuk.setKet(edtKet.getText().toString());
                }
                else {
                    dataMasuk.setKet("");
                }
                if (!edtNom.getText().toString().isEmpty()){
                    dataMasuk.setBiaya(edtNom.getText().toString());
                }
                else {
                    dataMasuk.setBiaya("");
                }

                int select_id=radioStatus.getCheckedRadioButtonId();
                radioStatusButton=(RadioButton)findViewById(select_id);
                dataMasuk.setStatus(radioStatusButton.getText().toString());

                dataMasuk.setTanggal(tglnya);

                dbHelper.insertData(dataMasuk);

                Intent intent=new Intent(MasukActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dbHelper=DbHelper.getInstance(getApplicationContext());
        DataMasuk dataMasuk=new DataMasuk();
        String tanggal1 = dayOfMonth+"-"+monthOfYear+"-"+year;
        tglnya=tanggal1;
    }
}