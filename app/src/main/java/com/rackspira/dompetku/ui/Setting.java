package com.rackspira.dompetku.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rackspira.dompetku.R;
import com.rackspira.dompetku.util.SharedPreferencesStorage;

public class Setting extends AppCompatActivity {

    TextView textViewUbahPin;
    String pin =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textViewUbahPin = (TextView)findViewById(R.id.textUbahPin);

        final SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage(Setting.this);
        pin = sharedPreferencesStorage.getPin();
        if(pin != null){
            textViewUbahPin.setText("Ubah Pin");
        }

        textViewUbahPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this,SettingPin.class);
                startActivity(intent);
            }
        });
    }
}
