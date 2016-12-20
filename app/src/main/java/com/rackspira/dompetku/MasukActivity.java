package com.rackspira.dompetku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rackspira.dompetku.database.CRUD;
import com.rackspira.dompetku.database.DataMasuk;
import com.rackspira.dompetku.database.DbHelper;

public class MasukActivity extends AppCompatActivity {
    DbHelper dbHelper;
    DataMasuk dataMasuk;
    private EditText edtKet, edtNom;
    private Button btnSave;
    Context context;
    String name;
    SQLiteDatabase.CursorFactory factory;
    int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        edtKet=(EditText)findViewById(R.id.ket);
        edtNom=(EditText)findViewById(R.id.nom);
        btnSave=(Button)findViewById(R.id.save);
        dbHelper=new DbHelper(context, name, factory, version);
        final CRUD crud=new CRUD(context, name, factory, version);
        dataMasuk=new DataMasuk();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(edtKet);
                System.out.println(edtNom);
                dataMasuk.ket=edtKet.getText().toString();
                dataMasuk.biaya=edtNom.getText().toString();
                crud.insertData(dataMasuk);
                finish();
            }
        });
    }
}