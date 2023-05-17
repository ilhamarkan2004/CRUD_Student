package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private Button login_button,register_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        initComponent();

        login_button.setOnClickListener(view -> login());
        register_btn.setOnClickListener(view -> register(login_email.getText().toString(),login_password.getText().toString()));
    }

    public void initComponent(){
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        register_btn = findViewById(R.id.register_button);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void login(){
        String input_email = login_email.getText().toString();
        String input_password = login_password.getText().toString();

        mAuth.signInWithEmailAndPassword(input_email, input_password).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_LONG).show();
                Intent op =  new Intent(LoginActivity.this,MainActivity.class);
                startActivity(op);
            }
            else {
                Toast.makeText(this, "Login gagal : " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void register(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    login_password.setText("");
                    login_email.setText("");
                    Toast.makeText(LoginActivity.this,"register berhasil",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(LoginActivity.this,"coba lagi",Toast.LENGTH_SHORT).show();

            }
        });

    }
}

