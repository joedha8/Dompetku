package com.rackspira.epenting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rackspira.epenting.model.GlobalDataMasuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kikiosha on 11/30/17.
 */

public class DbHutang extends SQLiteOpenHelper {
    protected static final String TAG = "DbHutang";

    private static final String DATABASE_NAME = "HutangDB";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_INPUT = "hutang_tabel";
    protected static final String TABLE_INPUT2 = "cicilan_tabel";

    protected static final String ID_HUTANG = "id_hutang";
    protected static final String PEMBERI_PINJAMAN = "pemberi_pinjaman";
    protected static final String NOMINAL = "nominal";
    protected static final String STATUS = "status";
    protected static final String TANGGAL_PINJAM = "tgl_pinjam";
    protected static final String TANGGAL_KEMBALI = "tgl_kembali";
    protected static final String CICILAN = "cicilan";
    protected static final String TANGGAL_BAYAR_CICILAN = "tgl_bayar_cicilan";

    private static DbHutang dbHutang;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateTabel = "create table " +
                TABLE_INPUT +
                " (" +
                ID_HUTANG + " integer primary key autoincrement not null," +
                PEMBERI_PINJAMAN + " text," +
                NOMINAL + " text," +
                STATUS + " text," +
                TANGGAL_PINJAM + " text," +
                TANGGAL_KEMBALI + " text" +
                ");";
        String queryCreateTabel2 = "create table " +
                TABLE_INPUT2 +
                " (" +
                ID_HUTANG + " integer primary key autoincrement not null," +
                PEMBERI_PINJAMAN + " text," +
                NOMINAL + " text," +
                STATUS + " text," +
                TANGGAL_PINJAM + " text," +
                CICILAN + " text," +
                TANGGAL_BAYAR_CICILAN + " text" +
                ");";

        sqLiteDatabase.execSQL(queryCreateTabel);
        sqLiteDatabase.execSQL(queryCreateTabel2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int lama, int baru) {
        if (lama != baru) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_INPUT);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_INPUT2);
            onCreate(sqLiteDatabase);
        }
    }

    public static synchronized DbHutang geiInstance(Context context){
        if (dbHutang == null) {
            dbHutang = new DbHutang(context.getApplicationContext());
        }
        return dbHutang;
    }

    DbHutang(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertHutang(Hutang hutang){
        SQLiteDatabase database=getWritableDatabase();
        database.beginTransaction();

        try {
            ContentValues values=new ContentValues();
            values.put(ID_HUTANG, hutang.getId_hutang());
            values.put(PEMBERI_PINJAMAN, hutang.getPemberiPinjaman());
            values.put(NOMINAL, hutang.getNominal());
            values.put(STATUS, hutang.getStatus());
            values.put(TANGGAL_PINJAM, hutang.getTgl_pinjam());
            values.put(TANGGAL_KEMBALI, hutang.getTgl_kembali());

            database.insertOrThrow(TABLE_INPUT, null, values);
            database.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Untuk Menambah");
        } finally {
            database.endTransaction();
        }
    }

    public void insertHutangCicilan(Hutang hutang){
        SQLiteDatabase database=getWritableDatabase();
        database.beginTransaction();

        try {
            ContentValues values=new ContentValues();
            values.put(ID_HUTANG, hutang.getId_hutang());
            values.put(PEMBERI_PINJAMAN, hutang.getPemberiPinjaman());
            values.put(NOMINAL, hutang.getNominal());
            values.put(STATUS, hutang.getStatus());
            values.put(TANGGAL_PINJAM, hutang.getTgl_pinjam());
            values.put(CICILAN, hutang.getCicilan());
            values.put(TANGGAL_BAYAR_CICILAN, hutang.getTgl_bayar_cicilan());

            database.insertOrThrow(TABLE_INPUT2, null, values);
            database.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Untuk Menambah");
        } finally {
            database.endTransaction();
        }
    }

    public List<Hutang> getHutang(){
        List<Hutang> hutangList=new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT;

        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()){
                do {
                    Hutang hutang=new Hutang(
                            cursor.getString(cursor.getColumnIndex(ID_HUTANG)),
                            cursor.getString(cursor.getColumnIndex(PEMBERI_PINJAMAN)),
                            cursor.getString(cursor.getColumnIndex(NOMINAL)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL_PINJAM)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL_KEMBALI)));
                    Log.d("id", cursor.getString(cursor.getColumnIndex(ID_HUTANG)));
                    hutangList.add(hutang);
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
        return hutangList;
    }

    public List<Hutang> getHutangCicilan(){
        List<Hutang> hutangList=new ArrayList<>();
        String DATA_MASUK_SELECT_QUERY = "SELECT * FROM " + TABLE_INPUT2;

        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery(DATA_MASUK_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()){
                do {
                    Hutang hutang=new Hutang(
                            cursor.getString(cursor.getColumnIndex(ID_HUTANG)),
                            cursor.getString(cursor.getColumnIndex(PEMBERI_PINJAMAN)),
                            cursor.getString(cursor.getColumnIndex(NOMINAL)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL_PINJAM)),
                            cursor.getString(cursor.getColumnIndex(CICILAN)),
                            cursor.getString(cursor.getColumnIndex(TANGGAL_BAYAR_CICILAN)));
                    Log.d("id", cursor.getString(cursor.getColumnIndex(ID_HUTANG)));
                    hutangList.add(hutang);
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
        return hutangList;
    }

    public void updateStatusHutang(String id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_INPUT + " SET " +
                    STATUS + " ='Lunas'" +
                    " WHERE " +
                    ID_HUTANG +" ='"+ id +"'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Update");
        } finally {
            db.endTransaction();
        }
    }

    public void updateStatusCicilan(String id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_INPUT2 + " SET " +
                    STATUS + " ='Lunas'" +
                    " WHERE " +
                    ID_HUTANG +" ='"+ id +"'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Gagal untuk Update");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteRowHutang(String id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_INPUT + " WHERE " +
                    ID_HUTANG + " = '" + id + "'");
            db.setTransactionSuccessful();
            Log.d("berhasi","Delet berhasil");
        } catch (SQLException e) {
            Log.d(TAG, "Gagal menghapus");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteRowCicilan(String id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_INPUT2 + " WHERE " +
                    ID_HUTANG + " = '" + id + "'");
            db.setTransactionSuccessful();
            Log.d("berhasi","Delet berhasil");
        } catch (SQLException e) {
            Log.d(TAG, "Gagal menghapus");
        } finally {
            db.endTransaction();
        }
    }
}