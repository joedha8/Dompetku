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
import android.widget.Toast;

import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;

public class MasukActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DbHelper dbHelper;
    private EditText edtKet, edtNom;
    private Button btnSave, tes;
    private RadioGroup radioStatus;
    private RadioButton radioStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        edtKet=(EditText)findViewById(R.id.ket);
        edtNom=(EditText)findViewById(R.id.nom);
        btnSave=(Button)findViewById(R.id.save);
        radioStatus=(RadioGroup)findViewById(R.id.stat);
        tes=(Button)findViewById(R.id.tes);
        dbHelper=DbHelper.getInstance(getApplicationContext());

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

                dbHelper.insertData(dataMasuk);

                Intent intent=new Intent(MasukActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_id=radioStatus.getCheckedRadioButtonId();
                radioStatusButton=(RadioButton)findViewById(select_id);
                Toast.makeText(MasukActivity.this, radioStatusButton.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void click(View view){
        final DataMasuk dataMasuk=new DataMasuk();
        RadioGroup stat=(RadioGroup)findViewById(R.id.stat);
        stat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case(R.id.radioMasuk):
                        dataMasuk.setStatus("Pemasukkan");
                        break;
                    case(R.id.radiokeluar):
                        dataMasuk.setStatus("Pengeluaran");
                        break;
                }
            }
        });
    }*/
}