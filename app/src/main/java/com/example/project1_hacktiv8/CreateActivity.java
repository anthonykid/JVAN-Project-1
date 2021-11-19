package com.example.project1_hacktiv8;

import android.app.DatePickerDialog;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class CreateActivity extends AppCompatActivity {

    DBHelper database;
    Button but_simpan;
    EditText nim_EditText, nama_EditText, prodi_EditText, alamat_EditText, tanggalLahir_EditText;
    RadioGroup jenisKelamin_RadioGroup, mahasiswaAktif_RadioGroup;
    Spinner prodiSpinner;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getSupportActionBar().setTitle("Tambah Mahasiswa");

        database = new DBHelper(this);

        nim_EditText = findViewById(R.id.nim_editText);
        nama_EditText = findViewById(R.id.nama_editText);
        alamat_EditText = findViewById(R.id.alamat_editText);
        tanggalLahir_EditText = findViewById(R.id.tanggallahir_editText);
        jenisKelamin_RadioGroup = findViewById(R.id.jenisKelamin_radioGroup);
        mahasiswaAktif_RadioGroup = findViewById(R.id.aktif_radioGroup);

        setupSpinner();
        setupCalender();

        but_simpan = findViewById(R.id.btnSave);
        but_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nim_EditText.getText().toString().isEmpty() || nama_EditText.getText().toString().isEmpty()) {
                    Toast.makeText(CreateActivity.this, "Silahkan mengisi NIM dan Nama", Toast.LENGTH_SHORT).show();
                } else {
                    insertToDatabase();
                }
            }
        });
    }

    public void setupSpinner() {
        prodiSpinner = findViewById(R.id.prodi_spinner);
        String[] items = new String[]{"Teknik Lingkungan", "Teknik Industri", "Seni Tari", "Seni Musik", "Manajemen", "Akuntasi", "Bhs Mandarin", "Teknik Perangkat Lunak", "Teknik Informatika", "Sistem Informasi"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        prodiSpinner.setAdapter(adapter);
    }

    public void insertToDatabase() {
        SQLiteDatabase db = database.getWritableDatabase();

        String uuid = UUID.randomUUID().toString();

        String jenisKelamin = jenisKelamin_RadioGroup.getCheckedRadioButtonId() ==
                R.id.laki_radioBtn ? "Laki-Laki" : "Perempuan";

        String isMahasiswaAktif = mahasiswaAktif_RadioGroup.getCheckedRadioButtonId() ==
                R.id.aktif_radioBtn ? "Mahasiswa Aktif" : "Mahasiswa Tidak Aktif";

        db.execSQL("INSERT INTO mahasiswa(id, nim, nama, prodi, alamat, tglLahir, jenisKelamin, mahasiswaAktif) VALUES('"
                + uuid + "', '"
                + nim_EditText.getText().toString() + "', '"
                + nama_EditText.getText().toString() + "', '"
                + prodiSpinner.getSelectedItem().toString() + "', '"
                + alamat_EditText.getText().toString() + "', '"
                + tanggalLahir_EditText.getText().toString() + "', '"
                + jenisKelamin + "', '"
                + isMahasiswaAktif
                +"')");

        Toast.makeText(this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();

        finish();
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
                new DatePickerDialog(CreateActivity.this, date, myCalendar
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

}
