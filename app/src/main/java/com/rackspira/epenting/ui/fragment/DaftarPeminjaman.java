package com.rackspira.epenting.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rackspira.epenting.MenuPilihan.RefreshHandler;
import com.rackspira.epenting.R;
import com.rackspira.epenting.chartutil.DemoBase;
import com.rackspira.epenting.database.DbHutang;
import com.rackspira.epenting.recyclerview.RecyclerViewAdapterPeminjaman;

/**
 * Created by kikiosha on 11/30/17.
 */

public class DaftarPeminjaman extends DemoBase implements RefreshHandler {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    RecyclerViewAdapterPeminjaman recyclerViewAdapterPeminjaman;
    DbHutang dbHutang;

    public DaftarPeminjaman() {
    }

    public static DaftarPeminjaman newInstance(String param1, String param2) {
        DaftarPeminjaman fragment=new DaftarPeminjaman();
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
        View view=inflater.inflate(R.layout.fragment_daftar_peminjaman, container, false);

        dbHutang=DbHutang.geiInstance(getContext());

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerviewPinjaman);
        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
        recyclerViewAdapterPeminjaman.notifyDataSetChanged();
    }

    public void refreshList(){
        recyclerViewAdapterPeminjaman=new RecyclerViewAdapterPeminjaman(getActivity(), dbHutang.getHutang());
        recyclerView.setAdapter(recyclerViewAdapterPeminjaman);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapterPeminjaman.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        super.onResume();
        refreshList();
        recyclerViewAdapterPeminjaman.notifyDataSetChanged();
    }
}
