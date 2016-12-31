package com.rackspira.dompetku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Tentang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
    }

    public void goToFB (View view) {
        goToUrl ( "https://www.facebook.com/RackSpiraFans/?fref=ts");
    }

    public void goToInstagram (View view) {
        goToUrl ( "https://www.instagram.com/rackspira/?hl=en");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
