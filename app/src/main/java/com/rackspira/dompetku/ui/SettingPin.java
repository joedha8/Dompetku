package com.rackspira.dompetku.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.common.Constants;
import com.rackspira.dompetku.util.SharedPreferencesStorage;

public class SettingPin extends AppCompatActivity {

    private PinLockView pinLockView;
    private IndicatorDots indicatorDots;
    private TextView textViewTitle;
    String temp;
    String pinLama = null;
    int i = 0;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting_pin);
        pinLockView = (PinLockView)findViewById(R.id.pin_lock_view);
        indicatorDots = (IndicatorDots)findViewById(R.id.indicator_dots);
        textViewTitle = (TextView)findViewById(R.id.textViewSetPin);
        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(pinLockListener);
        pinLockView.setPinLength(4);
        pinLockView.clearFocus();
        pinLockView.setTextColor(ContextCompat.getColor(this,R.color.color_primary));


        final SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage(SettingPin.this);
        pinLama = sharedPreferencesStorage.getPin();
        if(pinLama != null){

            textViewTitle.setText("Masukkan Pin Lama");
            i=-1;
        }
    }

    PinLockListener pinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if(i==-1){
                if (pinLama.equals(pin)){
                    i++;
                    textViewTitle.setText("Masukkan Pin baru");
                    pinLockView.resetPinLockView();
                }else{
                    pinLockView.resetPinLockView();
                    Toast.makeText(SettingPin.this,"Pin Salah!",Toast.LENGTH_SHORT).show();
                }
            }else if(i==0){
                i++;
                temp = pin;
                Log.d("temp1",temp);
                pinLockView.resetPinLockView();
                textViewTitle.setTextSize(18);
                textViewTitle.setText("Masukkan Pin Kembali");
            }else if(i==1){
                i++;
                Log.d("temp2",temp);
                if (temp.equals(pin)) {
                    pref = getSharedPreferences(Constants.PREF_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.PIN,pin);
                    editor.commit();
                    Intent intent = new Intent(SettingPin.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    i = 0;
                    pinLockView.resetPinLockView();
                    textViewTitle.setText("Atur Pin");
                    Toast.makeText(SettingPin.this, "Pin tidak cocok", Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
        }
    };
}
