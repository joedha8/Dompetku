package com.rackspira.dompetku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YUDHA on 19/12/2016.
 */

public class DbHelper extends SQLiteOpenHelper{

    protected static final String TAG="DbHelper";

    private static final String DATABASE_NAME="dompet";
    private static final int DATABASE_VERSION=1;

    protected static final String TABLE_PEMASUKKAN="pemasukkan";
    protected static final String TABLE_PENGELUARAN="pengeluaran";

    private static final String ID_MASUK="id_masuk";
    private static final String ID_KELUAR="id_keluar";
    protected static final String KET_MASUK="ket_masuk";
    protected static final String KET_KELUAR="ket_keluar";
    protected static final String BIAYA_MASUK="biaya_masuk";
    protected static final String BIAYA_KELUAR="biaya masuk";

    private static DbHelper dbHelper;

    public DbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_PEMASUKKAN+" ("+
                ID_MASUK+" INTEGER PRIMARY KEY AUTO INCREMENT, "+
                KET_MASUK+" TEXT, "+
                BIAYA_MASUK+" INTEGER);");

        db.execSQL("CREATE TABLE "+TABLE_PENGELUARAN+" ("+
                ID_KELUAR+" INTEGER PRIMARY KEY AUTO INCREMENT, "+
                KET_KELUAR+" TEXT, "+
                BIAYA_KELUAR+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int lama, int baru) {
        if(lama != baru){
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_PEMASUKKAN);

            db.execSQL("DROP TABLE IF EXISTS"+TABLE_PENGELUARAN);

            onCreate(db);
        }
    }

    public static synchronized DbHelper getInstance(Context context){
        if (dbHelper==null){
            dbHelper=new DbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
