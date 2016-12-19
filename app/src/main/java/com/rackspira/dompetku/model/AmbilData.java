package com.rackspira.dompetku.model;

/**
 * Created by WIN 10 on 19/12/2016.
 */

public class AmbilData {

    private String Keterangan1,Keterangan2;
    private int masuk,keluar;

    public String getKeterangan1() {
        return Keterangan1;
    }

    public void setKeterangan1(String keterangan1) {
        Keterangan1 = keterangan1;
    }

    public String getKeterangan2() {
        return Keterangan2;
    }

    public void setKeterangan2(String keterangan2) {
        Keterangan2 = keterangan2;
    }

    public int getMasuk() {
        return masuk;
    }

    public void setMasuk(int masuk) {
        this.masuk = masuk;
    }

    public int getKeluar() {
        return keluar;
    }

    public void setKeluar(int keluar) {
        this.keluar = keluar;
    }
}
