package com.rackspira.dompetku.model;

import com.rackspira.dompetku.database.DataMasuk;

/**
 * Created by YUDHA on 24/12/2016.
 */

public class GlobalDataMasuk {
    private static DataMasuk dataMasuk;

    public static void setDataMasuk(DataMasuk dataMasuk) {
        GlobalDataMasuk.dataMasuk = dataMasuk;
    }

    public static DataMasuk getDataMasuk() {
        return dataMasuk;
    }
}
