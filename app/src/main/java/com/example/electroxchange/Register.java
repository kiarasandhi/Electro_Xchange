package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText editEmail, editPass, editName;
    Button bRegis, bLogin;
    MyDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editTextTextEmailAddress2);
        editPass = findViewById(R.id.editTextTextPassword2);
        bRegis = findViewById(R.id.button3);
        bLogin = findViewById(R.id.button4);

        myDB = new MyDBHandler(this);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        bRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, pass;
                name = editName.getText().toString();
                email = editEmail.getText().toString();
                pass = editPass.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Register.this, "Enter Full Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }

                long result = myDB.addUser(name, email, pass);

                if (result != -1) {
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
