package com.rackspira.dompetku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUDHA on 19/12/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    protected static final String TAG = "DbHelper";

    private static final String DATABASE_NAME = "dompet";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_INPUT = "inputan";

    protected static final String KET = "ket";
    protected static final String BIAYA = "biaya";
    protected static final String STATUS = "status";
    protected static final String TANGGAL = "tanggal";

    private static DbHelper dbHelper;

    public int jumMasuk, jumKeluar;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_INPUT + "(" +
                KET + " TEXT," +
                BIAYA + " INTEGER," +
                STATUS + " TEXT," +
                TANGGAL + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int lama, int baru) {
        if (lama != baru) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_INPUT);

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
            values.put(KET, dataMasuk.getKet());
            values.put(BIAYA, dataMasuk.getBiaya());
            values.put(STATUS, dataMasuk.getStatus());
            values.put(TANGGAL, dataMasuk.getTanggal());

            db.insertOrThrow(TABLE_INPUT, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Menambah");
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
                    DataMasuk dataMasuk = new DataMasuk(cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)));
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
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT + " where " + STATUS + " ='Pemasukkan'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)));
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
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT + " where " + STATUS + " ='Pengeluaran'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    DataMasuk dataMasuk = new DataMasuk(cursor.getString(cursor.getColumnIndex(KET)),
                            cursor.getString(cursor.getColumnIndex(BIAYA)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL)));
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

    public int jumMasuk(){
        DataMasuk datamasuk=new DataMasuk();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorMasuk=db.rawQuery("select sum("+BIAYA+") from "+TABLE_INPUT+" where "+STATUS+" = 'Pemasukkan';",null);
        if(cursorMasuk.moveToFirst()) {
            jumMasuk = cursorMasuk.getInt(0);
            System.out.println(jumMasuk);
        }
        else{
            jumMasuk=-1;
            System.out.println(jumMasuk);
        }
        cursorMasuk.close();

        return jumMasuk;
    }

    public int jumKeluar(){
        DataMasuk datamasuk=new DataMasuk();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorKeluar=db.rawQuery("select sum("+BIAYA+") from "+TABLE_INPUT+" where "+STATUS+" = 'Pengeluaran';",null);
        if(cursorKeluar.moveToFirst()) {
            jumKeluar = cursorKeluar.getInt(0);
            System.out.println(jumKeluar);
        }
        else{
            jumKeluar=-1;
            System.out.println(jumKeluar);
        }
        cursorKeluar.close();

        return jumKeluar;
    }

    public void deleteRow(String ket) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_INPUT + " WHERE " + KET + " = '" + ket + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Gagal menghapus");
        } finally {
            db.endTransaction();
        }
    }
}