package com.rackspira.dompetku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUDHA on 19/12/2016.
 */

public class CRUD extends DbHelper {
    public CRUD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insertDataMasuk(DataMasuk dataMasuk){
        SQLiteDatabase db=getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values=new ContentValues();
            values.put(KET_MASUK, DataMasuk.ket_masuk);
            values.put(BIAYA_MASUK, DataMasuk.biaya_masuk);

            db.insertOrThrow(TABLE_PEMASUKKAN, null, values);
            db.setTransactionSuccessful();
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Menambah");
        }
        finally {
            db.endTransaction();
        }
    }

    public void insertDataKeluar(DataKeluar dataKeluar){
        SQLiteDatabase db=getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values=new ContentValues();
            values.put(KET_KELUAR, DataKeluar.ket_keluar);
            values.put(BIAYA_KELUAR, DataKeluar.biaya_keluar);

            db.insertOrThrow(TABLE_PENGELUARAN, null, values);
            db.setTransactionSuccessful();
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk menambah");
        }
        finally {
            db.endTransaction();
        }
    }

    public List<DataMasuk> getMasuk(){
        List<DataMasuk> dataMasukList=new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY="SELECT * FROM "+TABLE_PEMASUKKAN;

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    DataMasuk dataMasuk=new DataMasuk();

                    dataMasuk.ket_masuk=cursor.getString(cursor.getColumnIndex(KET_MASUK));
                    dataMasuk.biaya_masuk=cursor.getString(cursor.getColumnIndex(BIAYA_MASUK));

                    dataMasukList.add(dataMasuk);

                }
                while (cursor.moveToNext());
            }
        }
        catch (SQLException e){
            Log.d(TAG, "Gagal untuk menambah");
        }
        finally {
            if (cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return dataMasukList;
    }

    public List<DataKeluar> getKeluar(){
        List<DataKeluar> dataKeluarList=new ArrayList<>();
        String DATA_KELUAR_SELECT_QUERY="SELECT * FROM "+TABLE_PENGELUARAN;

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(DATA_KELUAR_SELECT_QUERY, null);

        try{
            DataKeluar dataKeluar=new DataKeluar();

            dataKeluar.ket_keluar=cursor.getString(cursor.getColumnIndex(KET_KELUAR));
            dataKeluar.biaya_keluar=cursor.getString(cursor.getColumnIndex(BIAYA_KELUAR));

            dataKeluarList.add(dataKeluar);
        }
        catch (SQLException e){
            Log.d(TAG, "Gagal untuk menambah");
        }
        finally {
            if (cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return dataKeluarList;
    }

    void deleteRow(String ket){
        SQLiteDatabase db=getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM "+TABLE_PEMASUKKAN+" WHERE "+KET_MASUK+" = '"+ket+"'");
            db.execSQL("DELETE FROM "+TABLE_PENGELUARAN+" WHERE "+KET_KELUAR+" = "+ket+"'");
            db.setTransactionSuccessful();
        }
        catch (SQLException e){
            Log.d(TAG, "Gagal menghapus");
        }
        finally {
            db.endTransaction();
        }
    }
}