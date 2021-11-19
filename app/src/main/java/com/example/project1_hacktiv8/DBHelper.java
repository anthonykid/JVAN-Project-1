package com.example.project1_hacktiv8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Myandroid.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE mahasiswa(id text NULL, nim text NULL, nama text NULL, prodi text NULL, alamat text NULL, tglLahir text NULL, jenisKelamin text NULL, mahasiswaAktif text NULL);";
        Log.d("Data", "onCreate"+ sql);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
