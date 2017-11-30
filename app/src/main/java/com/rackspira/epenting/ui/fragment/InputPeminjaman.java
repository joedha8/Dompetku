package com.rackspira.epenting.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.R;
import com.rackspira.epenting.chartutil.DemoBase;
import com.rackspira.epenting.database.DbHutang;
import com.rackspira.epenting.database.Hutang;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapterPeminjaman;
import com.rackspira.epenting.ui.Pemasukkan;

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

    EditText editTextPeminjam, editTextNominal, editTextTanggalPinjam, editTextTanggalKembali;
    Button buttonPinjam;
    DbHutang dbHutang;
    String datePinjam, dateKembali;

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
        buttonPinjam=(Button)view.findViewById(R.id.button_insert_pinjam);

        dbHutang=DbHutang.geiInstance(getContext());

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
                if (!editTextPeminjam.getText().toString().isEmpty() && !editTextNominal.getText().toString().isEmpty()){
                    Hutang hutang=new Hutang();

                    hutang.setPemberiPinjaman(""+editTextPeminjam.getText().toString());
                    hutang.setNominal(""+editTextNominal.getText().toString());
                    hutang.setTgl_pinjam(""+editTextTanggalPinjam.getText().toString());
                    hutang.setTgl_kembali(""+editTextTanggalKembali.getText().toString());

                    dbHutang.insertHutang(hutang);

                    editTextPeminjam.setText("");
                    editTextNominal.setText("");

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
        });

        return view;
    }
}
