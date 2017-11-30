package com.rackspira.epenting.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.support.v4.app.NotificationCompat;
import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DataMasuk;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.database.DbKategori;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MasukActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, RefreshHandler {
    SQLiteDatabase db;
    DbHelper dbHelper;
    DbKategori dbKategori;
    RecyclerViewAdapter adapter;
    String kat="pemasukkan";
    private EditText edtKet, edtNom;
    private Button btnSave;
    private RadioGroup radioStatus;
    private RadioButton radioStatusButton;
    private EditText tanggal;
    private String tglnya;
    private Spinner spinnerKategori;
    private static final  int NOTIFICATION_ID = 1;
    private int jmlPerKategori;
    private int batasPerKategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("TAMBAH DATA");

        edtKet=(EditText)findViewById(R.id.ket);
        edtNom=(EditText)findViewById(R.id.nom);
        btnSave=(Button)findViewById(R.id.save);
        tanggal=(EditText)findViewById(R.id.tgl);
        radioStatus=(RadioGroup)findViewById(R.id.stat);
        spinnerKategori=(Spinner)findViewById(R.id.spinner_kategori);
        dbHelper=DbHelper.getInstance(getApplicationContext());
        dbKategori=DbKategori.getInstance(getApplicationContext());

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        String tgl=sdf.format( new Date() );
        tanggal.setText(tgl);
        tglnya=tgl;

        System.out.println("Data Kategori : "+dbKategori.getKategori().size());
        final String[] kategori=new String[dbKategori.getKategori().size()];
        final String[] batasPerkategoris = new String[dbKategori.getKategori().size()];
        for (int i=0; i<dbKategori.getKategori().size(); i++){
            kategori[i]=dbKategori.getKategori().get(i).getKategori();
            batasPerkategoris[i] = dbKategori.getKategori().get(i).getBatasPengeluaran();
            System.out.println("Isi Kategori "+dbKategori.getKategori().get(i).getKategori());
        }
        ArrayAdapter<String> spinnerArrayAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, kategori);
        spinnerKategori.setAdapter(spinnerArrayAdapter);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index=spinnerKategori.getSelectedItemPosition();
                kat=kategori[i];
                Log.d("cara yudha",kategori[index]);
                Log.d("cara saya",kat);
                jmlPerKategori = dbHelper.biayaPerKategori(kat);
                batasPerKategori = Integer.parseInt( batasPerkategoris[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RadioButton radioButtonPemasukkan=(RadioButton)findViewById(R.id.radioMasuk);
        radioButtonPemasukkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerKategori.setVisibility(View.GONE);
            }
        });
        RadioButton radioButtonPengeluaran=(RadioButton)findViewById(R.id.radioKeluar);
        radioButtonPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerKategori.setVisibility(View.VISIBLE);
            }
        });

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

                int select_id=radioStatus.getCheckedRadioButtonId();
                radioStatusButton=(RadioButton)findViewById(select_id);
                dataMasuk.setStatus(radioStatusButton.getText().toString());
                dataMasuk.setKet(edtKet.getText().toString());
                dataMasuk.setBiaya(edtNom.getText().toString());
                dataMasuk.setKat(kat);
                dataMasuk.setTanggal(tglnya);

                if(!edtKet.getText().toString().isEmpty() && !edtNom.getText().toString().isEmpty() && !tanggal.getText().toString().isEmpty()){
                    dbHelper.insertData(dataMasuk);
                    Intent intent=new Intent(MasukActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if (edtKet.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"Keterangan tidak boleh kosong",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else if(edtNom.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"Nominal tidak boleh kosong",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else if(tanggal.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"tanggal tidak boleh kosong",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                Log.d("JmlKategori",jmlPerKategori+"");
                Log.d("Batass",batasPerKategori+"");
                int masukkan = Integer.parseInt(edtNom.getText().toString());
                if(jmlPerKategori > batasPerKategori){
                   notification();

                }else if (masukkan > batasPerKategori){
                    notification();
                }

            }
        });


    }

    private void notification() {

        Intent intentNotife = new Intent(getApplicationContext(),MainActivity.class);
        intentNotife.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),0,intentNotife,0);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle("E-Penting")
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setContentText("Pengeluaran anda Melebihi batas yang anda Tetapkan!");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        notificationManager.notify(NOTIFICATION_ID,builder.build());



    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tanggal1 = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        tanggal.setText(tanggal1);
        tglnya=tanggal1;
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            //finish();
            Intent intent=new Intent(MasukActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}