package com.rackspira.epenting.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rackspira.epenting.R;
import com.rackspira.epenting.chartutil.DemoBase;
import com.rackspira.epenting.database.DataMasuk;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.database.Hutang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kikiosha on 11/30/17.
 */

public class InputPeminjaman extends DemoBase {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextInputLayout textInputLayoutPinjam, textInputLayoutBayar, textInputLayoutCicilan, textInputLayoutBayarCicilan;
    EditText editTextPeminjam, editTextNominal, editTextTanggalPinjam, editTextTanggalKembali, editTextCicilan, editTextBayarCicilan;
    RadioGroup radioGroup;
    RadioButton radioButtonYa, radioButtonTidak;
    Button buttonPinjam;
    DbHelper dbHelper;
    String datePinjam, dateKembali;
    String status;

    public InputPeminjaman() {
    }

    public static InputPeminjaman newInstance(String param1, String param2) {
        InputPeminjaman fragment=new InputPeminjaman();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_input_peminjaman, container, false);

        editTextPeminjam=(EditText)view.findViewById(R.id.pinjam_ke);
        editTextNominal=(EditText)view.findViewById(R.id.pinjam_nominal);
        editTextTanggalPinjam=(EditText)view.findViewById(R.id.tgl_pinjam);
        editTextTanggalKembali=(EditText)view.findViewById(R.id.pinjam_kembali);
        editTextCicilan=(EditText)view.findViewById(R.id.cicilan);
        editTextBayarCicilan=(EditText)view.findViewById(R.id.tgl_bayar_cicilan);
        buttonPinjam=(Button)view.findViewById(R.id.button_insert_pinjam);
        radioGroup=(RadioGroup)view.findViewById(R.id.groupCicilan);
        radioButtonYa=(RadioButton)view.findViewById(R.id.radioYa);
        radioButtonTidak=(RadioButton)view.findViewById(R.id.radioTidak);
        textInputLayoutPinjam=(TextInputLayout)view.findViewById(R.id.input_layout_tanggal_pinjam);
        textInputLayoutBayar=(TextInputLayout)view.findViewById(R.id.input_layout_tanggal_kembali);
        textInputLayoutCicilan=(TextInputLayout)view.findViewById(R.id.input_layout_cicilan);
        textInputLayoutBayarCicilan=(TextInputLayout)view.findViewById(R.id.input_layout_bayar_cicilan);

        dbHelper =DbHelper.getInstance(getContext());

        radioButtonYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="ya";
                textInputLayoutPinjam.setVisibility(View.VISIBLE);
                textInputLayoutCicilan.setVisibility(View.VISIBLE);
                textInputLayoutBayarCicilan.setVisibility(View.VISIBLE);
                textInputLayoutBayar.setVisibility(View.GONE);
            }
        });
        radioButtonTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="tidak";
                textInputLayoutPinjam.setVisibility(View.VISIBLE);
                textInputLayoutBayar.setVisibility(View.VISIBLE);
                textInputLayoutCicilan.setVisibility(View.GONE);
                textInputLayoutBayarCicilan.setVisibility(View.GONE);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        String tanggalAwal=sdf.format( new Date() );
        editTextTanggalPinjam.setText(tanggalAwal);
        datePinjam=tanggalAwal;

        SimpleDateFormat sdf1 = new SimpleDateFormat( "dd-MM-yyyy" );
        String tanggalAkhir=sdf1.format( new Date() );
        editTextTanggalKembali.setText(tanggalAkhir);
        dateKembali=tanggalAkhir;

        editTextTanggalPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int tahun=calendar.get(Calendar.YEAR);
                int bulan=calendar.get(Calendar.MONTH);
                int hari=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date=day+"-"+(month+1)+"-"+year;
                        editTextTanggalPinjam.setText(date);
                        datePinjam=date;
                    }
                }, tahun, bulan, hari);
                datePickerDialog.show();
            }
        });

        editTextTanggalKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int tahun=calendar.get(Calendar.YEAR);
                int bulan=calendar.get(Calendar.MONTH);
                int hari=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date=day+"-"+(month+1)+"-"+year;
                        editTextTanggalKembali.setText(date);
                        dateKembali=date;
                    }
                }, tahun, bulan, hari);
                datePickerDialog.show();
            }
        });

        buttonPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status=="ya"){
                    if (!editTextPeminjam.getText().toString().isEmpty() &&
                            !editTextNominal.getText().toString().isEmpty() &&
                            !editTextCicilan.getText().toString().isEmpty() &&
                            !editTextTanggalPinjam.getText().toString().isEmpty() &&
                            !editTextBayarCicilan.getText().toString().isEmpty()){
                        int tglByrCcl=Integer.parseInt(editTextBayarCicilan.getText().toString());
                        if (tglByrCcl<=31 && tglByrCcl>=1){
                            Hutang hutang=new Hutang();

                            hutang.setPemberiPinjaman(""+editTextPeminjam.getText().toString());
                            hutang.setNominal(""+editTextNominal.getText().toString());
                            hutang.setStatus("Belum Lunas");
                            hutang.setCicilan(""+editTextCicilan.getText().toString());
                            hutang.setTgl_pinjam(""+editTextTanggalPinjam.getText().toString());
                            hutang.setTgl_bayar_cicilan(""+editTextBayarCicilan.getText().toString());

                            dbHelper.insertHutangCicilan(hutang);

                            clear();
                        } else {
                            Toast.makeText(getContext(), "Tidak ada tanggal "+tglByrCcl, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Isi Data Dengan Benar", Toast.LENGTH_SHORT).show();
                    }
                } else if (status=="tidak"){
                    if (!editTextPeminjam.getText().toString().isEmpty() && !editTextNominal.getText().toString().isEmpty()) {
                        Hutang hutang = new Hutang();
                        DataMasuk dataMasuk = new DataMasuk();

                        hutang.setPemberiPinjaman("" + editTextPeminjam.getText().toString());
                        hutang.setNominal("" + editTextNominal.getText().toString());
                        hutang.setStatus("Belum Lunas");
                        hutang.setTgl_pinjam("" + editTextTanggalPinjam.getText().toString());
                        hutang.setTgl_kembali("" + editTextTanggalKembali.getText().toString());

                        dataMasuk.setKet("Hutang "+editTextPeminjam.getText().toString());
                        dataMasuk.setBiaya("" + editTextNominal.getText().toString());
                        dataMasuk.setStatus("Pemasukkan");
                        dataMasuk.setKat("pemasukkan");
                        dataMasuk.setTanggal("" + editTextTanggalPinjam.getText().toString());

                        dbHelper.insertHutang(hutang);
                        dbHelper.insertData(dataMasuk);

                        clear();
                    } else {
                        Toast.makeText(getContext(), "Isi Data Dengan Benar", Toast.LENGTH_SHORT).show();
                    }
                }
                reload();
            }
        });
        return view;
    }

    public void clear(){
        editTextPeminjam.setText("");
        editTextNominal.setText("");
        radioButtonYa.setChecked(false);
        radioButtonTidak.setChecked(false);
    }

    public void reload(){
        new Handler().post(new Runnable() {

            @Override
            public void run()
            {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
    }
}
