package com.example.project1_hacktiv8;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    DBHelper database;
    Button but_simpan;
    EditText nim_EditText, nama_EditText, prodi_EditText, alamat_EditText, tanggalLahir_EditText;
    RadioGroup jenisKelamin_RadioGroup, mahasiswaAktif_RadioGroup;
    Spinner prodiSpinner;

    final Calendar myCalendar = Calendar.getInstance();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setTitle("Edit Mahasiswa");

        database = new DBHelper(this);

        nim_EditText = findViewById(R.id.nim_editText);
        nama_EditText = findViewById(R.id.nama_editText);
        alamat_EditText = findViewById(R.id.alamat_editText);
        tanggalLahir_EditText = findViewById(R.id.tanggallahir_editText);
        jenisKelamin_RadioGroup = findViewById(R.id.jenisKelamin_radioGroup);
        mahasiswaAktif_RadioGroup = findViewById(R.id.aktif_radioGroup);

        setupCalender();
        setupSpinner();

        but_simpan = findViewById(R.id.btnSave);
        but_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nim_EditText.getText().toString().isEmpty() || nama_EditText.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Silahkan mengisi NIM dan Nama", Toast.LENGTH_SHORT).show();
                } else {
                    updateGame();
                }
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        getDetail(id);
    }

    public void setupSpinner() {
        prodiSpinner = findViewById(R.id.prodi_spinner);
        String[] items = new String[]{"Teknik Lingkungan",
                "Teknik Industri",
                "Seni Tari",
                "Seni Musik",
                "Manajemen",
                "Akuntasi",
                "Bhs Mandarin",
                "Teknik Perangkat Lunak",
                "Teknik Informatika",
                "Sistem Informasi"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        prodiSpinner.setAdapter(adapter);
    }

    public void setupCalender() {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        tanggalLahir_EditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        tanggalLahir_EditText.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateGame() {
        SQLiteDatabase db = database.getWritableDatabase();

        String jenisKelamin = jenisKelamin_RadioGroup.getCheckedRadioButtonId() ==
                R.id.laki_radioBtn ? "Laki-Laki" : "Perempuan";

        String isMahasiswaAktif = mahasiswaAktif_RadioGroup.getCheckedRadioButtonId() ==
                R.id.aktif_radioBtn ? "Mahasiswa Aktif" : "Mahasiswa Tidak Aktif";

        db.execSQL("UPDATE mahasiswa SET nim='" +
                        nim_EditText.getText().toString() + "', nama = '" +
                        nama_EditText.getText().toString() + "', prodi = '" +
                        prodiSpinner.getSelectedItem().toString() + "', alamat = '" +
                        alamat_EditText.getText().toString() + "', tglLahir = '" +
                        tanggalLahir_EditText.getText().toString() + "', jenisKelamin = '" +
                        jenisKelamin + "', mahasiswaAktif = '" +
                        isMahasiswaAktif + "' WHERE id = '" +
                        id + "'");

        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void getDetail(String id) {

        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa WHERE id = '" + id + "'", null);
        cursor.moveToFirst();
        String[] items = new String[]{"Teknik Lingkungan", "Teknik Industri", "Seni Tari", "Seni Musik", "Manajemen", "Akuntasi", "Bhs Mandarin", "Teknik Perangkat Lunak", "Teknik Informatika", "Sistem Informasi"};
    
        String prodi = cursor.getString(3);
        int prodiIndex = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == prodi) {
                prodiIndex = i;
            }
        }
        
        nim_EditText.setText(cursor.getString(1));
        nama_EditText.setText(cursor.getString(2));
        prodiSpinner.setSelection(prodiIndex);
        alamat_EditText.setText(cursor.getString(4));
        tanggalLahir_EditText.setText(cursor.getString(5));

        if (cursor.getString(6) == "Laki-Laki") {
            jenisKelamin_RadioGroup.check(R.id.laki_radioBtn);
        } else {
            jenisKelamin_RadioGroup.check(R.id.perempuan_radioBtn);
        }

        if (cursor.getString(7) == "Mahasiswa Aktif") {
            mahasiswaAktif_RadioGroup.check(R.id.aktif_radioBtn);
        } else {
            mahasiswaAktif_RadioGroup.check(R.id.inaktif_radioBtn);
        }
    }

}