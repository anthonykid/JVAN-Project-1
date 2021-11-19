package com.example.project1_hacktiv8;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    DBHelper database;
    TextView nimTV, namaTV, prodiTV, alamatTV, tanggalLahirTV, jenisKelaminTV, statusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = new DBHelper(this);

        nimTV = findViewById(R.id.nim_textView);
        namaTV = findViewById(R.id.nama_textView);
        alamatTV = findViewById(R.id.alamat_textView);
        prodiTV = findViewById(R.id.prodi_textView);
        tanggalLahirTV = findViewById(R.id.tanggallahir_textView);
        jenisKelaminTV = findViewById(R.id.jenisKelamin_textView);
        statusTV = findViewById(R.id.status_textView);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        getSupportActionBar().setTitle("Detail Mahasiswa");

        getDetail(id);
    }

    public void getDetail(String id) {

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa WHERE id = '" + id + "'", null);
        cursor.moveToFirst();

        nimTV.setText(cursor.getString(1));
        namaTV.setText(cursor.getString(2));
        alamatTV.setText(cursor.getString(3));
        prodiTV.setText(cursor.getString(4));
        tanggalLahirTV.setText(cursor.getString(5));
        jenisKelaminTV.setText(cursor.getString(6));
        statusTV.setText(cursor.getString(7));

    }

}