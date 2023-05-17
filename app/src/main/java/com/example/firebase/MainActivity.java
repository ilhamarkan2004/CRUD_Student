package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView welcome, email, tv_uid;
    private Button logOutButton, getDataButton;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initComponent();
        displayProfile();

        logOutButton.setOnClickListener(view -> logOut());
        getDataButton.setOnClickListener(view -> getData());
    }

    private void initComponent() {
        welcome = findViewById(R.id.welcome);
        email = findViewById(R.id.email);
        tv_uid= findViewById(R.id.tv_uid);
        logOutButton = findViewById(R.id.logoutButton);
        getDataButton = findViewById(R.id.listButton);
    }

    private void displayProfile() {
        if (firebaseUser != null) {
            welcome.setText("Selamat Datang " + firebaseUser.getDisplayName());
            email.setText(firebaseUser.getEmail());
            tv_uid.setText(firebaseUser.getUid());
        } else {
            Toast.makeText(this, "Anda belum login. Silakan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }


    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Berhasil logout", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void getData() {
        startActivity(new Intent(MainActivity.this, MahasiswaActivity.class));
    }
}


