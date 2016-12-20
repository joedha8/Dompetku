package com.rackspira.dompetku.database;

import java.io.Serializable;

/**
 * Created by YUDHA on 19/12/2016.
 */

public class DataMasuk implements Serializable {
    private String ket;
    private String biaya;
    private String status;

    public DataMasuk(String ket, String biaya, String status) {
        this.ket = ket;
        this.biaya = biaya;
        this.status = status;
    }

    public DataMasuk() {
    }

    public String getKet() {
        return ket;
    }

    public String getBiaya() {
        return biaya;
    }

    public String getStatus() {
        return status;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}