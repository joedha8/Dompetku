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

    protected static final String TABLE_INPUT="inputan";

    private static final String ID="id";
    protected static final String KET="ket";
    protected static final String BIAYA="biaya";

    private static DbHelper dbHelper;

    public DbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_INPUT+" ("+
                ID+" INTEGER PRIMARY KEY AUTO INCREMENT, "+
                KET+" TEXT, "+
                BIAYA+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int lama, int baru) {
        if(lama != baru){
            db.execSQL("DROP TABLE IF EXISTS"+TABLE_INPUT);

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
