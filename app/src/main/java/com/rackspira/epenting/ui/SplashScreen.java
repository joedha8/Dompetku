package com.rackspira.epenting.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.rackspira.epenting.R;
import com.rackspira.epenting.util.SharedPreferencesStorage;

public class SplashScreen extends AppCompatActivity {
    private  static  int splashDurasi = 2000;
    private String pin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage(SplashScreen.this);
                pin = sharedPreferencesStorage.getPin();
                Log.d("pin","t" +pin);
                if(pin != null){
                    Intent intent = new Intent(SplashScreen.this,PinView.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }


                this.finish();
            }
            private void finish(){

            }
        },splashDurasi);
    }
}
