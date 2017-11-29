package com.rackspira.epenting.database;

import java.io.Serializable;

/**
 * Created by kikiosha on 11/13/17.
 */

public class Kategori implements Serializable {
    private String id;
    private String kategori;

    public Kategori(String id, String kategori) {
        this.id = id;
        this.kategori = kategori;
    }

    public Kategori() {
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
