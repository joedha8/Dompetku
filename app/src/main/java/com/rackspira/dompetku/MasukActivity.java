package com.rackspira.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;

public class MasukActivity extends AppCompatActivity {
    DbHelper dbHelper;
    private EditText edtKet, edtNom;
    private Button btnSave;
    private RadioButton masuk, keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        edtKet=(EditText)findViewById(R.id.ket);
        edtNom=(EditText)findViewById(R.id.nom);
        btnSave=(Button)findViewById(R.id.save);
        masuk=(RadioButton)findViewById(R.id.radioMasuk);
        keluar=(RadioButton)findViewById(R.id.radiokeluar);
        dbHelper=DbHelper.getInstance(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataMasuk dataMasuk=new DataMasuk();

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
                switch (view.getId()){
                    case R.id.radioMasuk: dataMasuk.setStatus("Pemasukkan"); break;
                    case R.id.radiokeluar: dataMasuk.setStatus("Pengeluaran"); break;
                }

                dbHelper.insertData(dataMasuk);

                Intent intent=new Intent(MasukActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}