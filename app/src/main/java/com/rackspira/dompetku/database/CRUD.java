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

    public void insertData(DataMasuk dataMasuk){
        SQLiteDatabase db=getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values=new ContentValues();
            values.put(KET, DataMasuk.ket);
            values.put(BIAYA, DataMasuk.biaya);

            db.insertOrThrow(TABLE_INPUT, null, values);
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

    public List<DataMasuk> getMasuk(){
        List<DataMasuk> dataMasukList=new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY="SELECT * FROM "+TABLE_INPUT;

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    DataMasuk dataMasuk=new DataMasuk();

                    dataMasuk.ket=cursor.getString(cursor.getColumnIndex(KET));
                    dataMasuk.biaya=cursor.getString(cursor.getColumnIndex(BIAYA));

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

    void deleteRow(String ket){
        SQLiteDatabase db=getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM "+TABLE_INPUT+" WHERE "+KET+" = '"+ket+"'");
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