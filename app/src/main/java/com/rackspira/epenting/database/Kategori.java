package com.rackspira.epenting.database;

import java.io.Serializable;

/**
 * Created by kikiosha on 11/13/17.
 */

public class Kategori implements Serializable {
    private String id;
    private String kategori;
    private String batasPengeluaran;

    public Kategori(String id, String kategori, String batasPengeluaran) {
        this.id = id;
        this.kategori = kategori;
        this.batasPengeluaran = batasPengeluaran;
    }

    public Kategori() {
    }

    public String getBatasPengeluaran() {
        return batasPengeluaran;
    }

    public void setBatasPengeluaran(String batasPengeluaran) {
        this.batasPengeluaran = batasPengeluaran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
