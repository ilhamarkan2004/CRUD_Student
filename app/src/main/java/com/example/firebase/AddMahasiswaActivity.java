package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMahasiswaActivity extends AppCompatActivity {
    private TextView titleTextView;
    private EditText namaEditText, nimEditText;
    private Button submitButton;
    private DAOMahasiswa DAOMahasiswa = new DAOMahasiswa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        initComponent();

        submitButton.setOnClickListener(view -> add());
    }

    private void initComponent() {
        titleTextView = findViewById(R.id.addTitle);
        namaEditText = findViewById(R.id.input_nama);
        nimEditText = findViewById(R.id.input_nim);
        submitButton = findViewById(R.id.submitButton);

        titleTextView.setText("Form Tambah Anggota");
    }

    private void add() {
        String nama = namaEditText.getText().toString();
        String nim = nimEditText.getText().toString();
        Mahasiswa mahasiswa = new Mahasiswa(nama, nim);

        DAOMahasiswa.insert(mahasiswa).addOnSuccessListener(success -> {
            Toast.makeText(this, "Berhasil menambah mahasiswa!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }).addOnFailureListener(error -> Toast.makeText(
                this,
                "Gagal menambahkan anggota: " + error.getLocalizedMessage(),
                Toast.LENGTH_LONG
        ).show());
    }
}

