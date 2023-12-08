package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText editEmail, editPass;
    Button bLogin, bRegis;
    MyDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editTextTextEmailAddress);
        editPass = findViewById(R.id.editTextTextPassword);
        bLogin = findViewById(R.id.button);
        bRegis = findViewById(R.id.button2);

        myDB = new MyDBHandler(this);

        bRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = editEmail.getText().toString();
                pass = editPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }

                int userId = myDB.getUserId(email, pass);

                if (userId != -1) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    mainIntent.putExtra("USER_EMAIL", email);
                    mainIntent.putExtra("USER_ID", userId);

                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
