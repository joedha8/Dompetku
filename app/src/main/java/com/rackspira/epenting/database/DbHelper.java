package com.rackspira.epenting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rackspira.epenting.model.GlobalDataMasuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUDHA on 19/12/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    protected static final String TAG = "DbHelper";

    public static final String DATABASE_NAME = "epenting";
    private static final int DATABASE_VERSION = 3;

    protected static final String TABLE_INPUT = "tbKeuangan";

    protected static final String KET = "ket";
    protected static final String BIAYA = "biaya";
    protected static final String STATUS = "status";
    protected static final String TANGGAL = "tanggal";
    protected static final String ID = "id";
    protected static final String KATEGORI = "kat";

    private static DbHelper dbHelper;

    public int jumMasuk, jumKeluar, jumKeluarBulanan, jumMasukBulannan, biayaPerKategori;

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryCreateTabel = "create table " +
                TABLE_INPUT +
                " (" +
                ID + " integer primary key autoincrement not null," +
                KET + " text," +
                BIAYA + " integer," +
                STATUS + " text," +
                TANGGAL + " text," +
                KATEGORI + " text" +
                ");";

        db.execSQL(queryCreateTabel);

//        db.execSQL("CREATE TABLE " + TABLE_INPUT + "(" +
//                ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                KET + " TEXT," +
//                BIAYA + " INTEGER," +
//                STATUS + " TEXT," +
//                TANGGAL + " TEXT," +
//                KATEGORI + "TEXT" +
//                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int lama, int baru) {
        if (lama != baru) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INPUT);
            onCreate(db);
        }
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertData(DataMasuk dataMasuk) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(KATEGORI, dataMasuk.getKat());
            values.put(STATUS, dataMasuk.getStatus());
            values.put(TANGGAL, dataMasuk.getTanggal());
            values.put(BIAYA, dataMasuk.getBiaya());
            values.put(KET, dataMasuk.getKet());

            db.insertOrThrow(TABLE_INPUT, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Menambah" + e);
        } finally {
            db.endTransaction();
        }
    }

    public List<DataMasuk> getMasuk() {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));

                    Log.d("id",cursor.getString(cursor.getColumnIndex(ID)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataMasuk> getPemasukkan() {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " +
                TABLE_INPUT +
                " where " + STATUS + " ='Pemasukkan'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataMasuk> sortByKategori(String kategori) {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " +
                TABLE_INPUT +
                " where " + KATEGORI + " ='"+kategori+"'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataMasuk> getPengeluaran() {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " +
                TABLE_INPUT +
                " where " + STATUS + " ='Pengeluaran'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataMasuk> getPemasukkan(String dateAwal, String dateAkhir) {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT + " where " +
                STATUS + " ='Pemasukkan' AND " +
                TANGGAL + " between '" + dateAwal + "' AND '" + dateAkhir + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataMasuk> getPengeluaran(String dataAwal, String dataAkhir) {
        List<DataMasuk> dataMasukList = new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT + " where " +
                STATUS + " ='Pengeluaran' AND " +
                TANGGAL + " between '" + dataAwal + "' AND '" + dataAkhir + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(
                            cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)),
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    dataMasukList.add(dataMasuk);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public int jumMasuk() {
        DataMasuk datamasuk = new DataMasuk();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorMasuk = db.rawQuery("select sum(" + BIAYA + ") from " + TABLE_INPUT + " where " + STATUS + " = 'Pemasukkan';", null);
        if (cursorMasuk.moveToFirst()) {
            jumMasuk = cursorMasuk.getInt(0);
            System.out.println(jumMasuk);
        } else {
            jumMasuk = -1;
            System.out.println(jumMasuk);
        }
        cursorMasuk.close();

        return jumMasuk;
    }

    public int jumKeluar() {
        DataMasuk datamasuk = new DataMasuk();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorKeluar = db.rawQuery("select sum(" + BIAYA + ") from " + TABLE_INPUT + " where " + STATUS + " = 'Pengeluaran';", null);
        if (cursorKeluar.moveToFirst()) {
            jumKeluar = cursorKeluar.getInt(0);
            System.out.println(jumKeluar);
        } else {
            jumKeluar = -1;
            System.out.println(jumKeluar);
        }
        cursorKeluar.close();

        return jumKeluar;
    }

    public int biayaPerKategori(String kat) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorKeluar = db.rawQuery("select sum(" + BIAYA + ") from " + TABLE_INPUT + " where " + KATEGORI + " = '"+kat+"';", null);
        if (cursorKeluar.moveToFirst()) {
            biayaPerKategori = cursorKeluar.getInt(0);
            System.out.println(biayaPerKategori);
        } else {
            biayaPerKategori = -1;
            System.out.println(biayaPerKategori);
        }
        cursorKeluar.close();

        return biayaPerKategori;
    }

    public int jumKeluarBulanan(int bulan, int tahun){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorKeluar = db.rawQuery("select sum(" + BIAYA + ") from " + TABLE_INPUT + " where " + STATUS + " = 'Pengeluaran' and " +
                TANGGAL + " like '%"+bulan+"-"+tahun+"';", null);
        if (cursorKeluar.moveToFirst()) {
            jumKeluarBulanan = cursorKeluar.getInt(0);
            System.out.println(jumKeluarBulanan);
        } else {
            jumKeluarBulanan = -1;
            System.out.println(jumKeluarBulanan);
        }
        cursorKeluar.close();

        return jumKeluarBulanan;
    }

    public int jumMasukBulanan(int bulan, int tahun){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorMasuk = db.rawQuery("select sum(" + BIAYA + ") from " + TABLE_INPUT + " where " + STATUS + " = 'Pemasukkan' and " +
                TANGGAL + " like '%"+bulan+"-"+tahun+"';", null);
        if (cursorMasuk.moveToFirst()) {
            jumMasukBulannan = cursorMasuk.getInt(0);
            System.out.println(jumMasukBulannan);
        } else {
            jumMasukBulannan = -1;
            System.out.println(jumMasukBulannan);
        }
        cursorMasuk.close();

        return jumMasukBulannan;
    }

    public void deleteRow(String ket, String nom, String tgl) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_INPUT + " WHERE " +
                    KET + " = '" + ket + "' AND " +
                    BIAYA + " = '" + nom + "' AND " +
                    TANGGAL + " = '" + tgl + "'");
            db.setTransactionSuccessful();
            Log.d("berhasi","Delet berhasil");
        } catch (SQLException e) {
            Log.d(TAG, "Gagal menghapus");
        } finally {
            db.endTransaction();
        }
    }

    public void updateData(DataMasuk dataMasuk) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_INPUT + " SET " +
                    KET + " ='" + dataMasuk.getKet() + "', " +
                    BIAYA + " ='" + dataMasuk.getBiaya() + "', " +
                    TANGGAL + " ='" + dataMasuk.getTanggal() +
                    KET + " ='" + dataMasuk.getKet() + "', " +
                    "' WHERE " +
                    ID +" ='"+ GlobalDataMasuk.getDataMasuk().getId() +"'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Menambah");
        } finally {
            db.endTransaction();
        }
    }
}