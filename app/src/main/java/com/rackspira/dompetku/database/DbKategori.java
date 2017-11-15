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
 * Created by kikiosha on 11/13/17.
 */

public class DbKategori extends SQLiteOpenHelper {
    protected static final String TAG = "DbKategori";

    private static final String DATABASE_NAME = "KategoriDB";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_INPUT = "kategori_tabel";

    protected static final String ID = "id";
    protected static final String KATEGORI = "kategori";

    private static DbKategori dbKategori;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INPUT + "(" +
                ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KATEGORI + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int lama, int baru) {
        if (lama != baru) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_INPUT);
            onCreate(sqLiteDatabase);
        }
    }
    public static synchronized DbKategori getInstance(Context context) {
        if (dbKategori == null) {
            dbKategori = new DbKategori(context.getApplicationContext());
        }
        return dbKategori;
    }

    DbKategori(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertKategori(Kategori kategori){
        SQLiteDatabase database=getWritableDatabase();
        database.beginTransaction();

        try {
            ContentValues values=new ContentValues();
            values.put(ID, kategori.getId());
            values.put(KATEGORI, kategori.getKategori());

            database.insertOrThrow(TABLE_INPUT, null, values);
            database.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Untuk Menambah");
        } finally {
            database.endTransaction();
        }
    }

    public List<Kategori> getKategori(){
        List<Kategori> kategoriList=new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT;

        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()){
                do {
                    Kategori kategori=new Kategori(
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(KATEGORI)));
                    Log.d("id", cursor.getString(cursor.getColumnIndex(ID)));
                    kategoriList.add(kategori);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e){
            Log.d(TAG, "Gagal untuk menambah");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return kategoriList;
    }
}
