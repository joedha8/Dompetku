package com.rackspira.dompetku.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.rackspira.dompetku.R;
import com.rackspira.dompetku.util.SharedPreferencesStorage;

public class PinView extends Activity {

    private PinLockView pinLockView;
    private IndicatorDots indicatorDots;
    String pinShare = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_view);

        pinLockView = (PinLockView)findViewById(R.id.pin_lock_view);
        indicatorDots = (IndicatorDots)findViewById(R.id.indicator_dots);

        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(pinLockListener);
        pinLockView.setPinLength(4);
        pinLockView.setTextColor(ContextCompat.getColor(this,R.color.textWhite));
    }

    PinLockListener pinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            final SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage(PinView.this);
            pinShare = sharedPreferencesStorage.getPin();
            if(pinShare.equals(pin)){
                Intent intent = new Intent(PinView.this,MainActivity.class);
                startActivity(intent);
            }else{
                pinLockView.resetPinLockView();
                Toast.makeText(PinView.this,"Pin Salah!",Toast.LENGTH_SHORT).show();

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
