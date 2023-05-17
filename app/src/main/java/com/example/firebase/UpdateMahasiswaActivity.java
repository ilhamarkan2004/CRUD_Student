package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateMahasiswaActivity extends AppCompatActivity {
    private TextView titleTextView;
    private EditText namaEditText, nimEditText;
    private Button submitButton;
    private DAOMahasiswa DAOMahasiswa = new DAOMahasiswa();
    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        initComponent();
        String key = getIntent().getStringExtra("KEY");
        getMahasiswa(key);

        submitButton.setOnClickListener(view -> update(key));
    }

    private void initComponent() {
        titleTextView = findViewById(R.id.addTitle);
        namaEditText = findViewById(R.id.input_nama);
        nimEditText = findViewById(R.id.input_nim);
        submitButton = findViewById(R.id.submitButton);

        titleTextView.setText("Form Edit Mahasiswa");
    }

    private void getMahasiswa(String key) {
        DAOMahasiswa.getByKey(key).addOnCompleteListener(task -> {
            if (!task.isSuccessful())
                return;
            mahasiswa = task.getResult().getValue(Mahasiswa.class);
            namaEditText.setText(mahasiswa.getNama());
            nimEditText.setText(mahasiswa.getNim());
        });
    }

    private void update(String key) {
        String nama = namaEditText.getText().toString();
        String nim = nimEditText.getText().toString();

        mahasiswa.setNama(nama);
        mahasiswa.setNim(nim);

        DAOMahasiswa.update(key, mahasiswa).addOnSuccessListener(res -> {
            Toast.makeText(
                    this,
                    "Update berhasil",
                    Toast.LENGTH_LONG
            ).show();
            startActivity(new Intent(this, MainActivity.class));
        }).addOnFailureListener(error -> Toast.makeText(
                this,
                "Update gagal: " + error.getLocalizedMessage(),
                Toast.LENGTH_LONG
        ).show());
    }
}

