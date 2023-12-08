package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile extends AppCompatActivity {

    Button bAdd, bAkun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        bAdd = findViewById(R.id.button10);
        bAkun = findViewById(R.id.button6);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, AddProduct.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        bAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, PengaturanAkun.class);
                startActivity(intent);
            }
        });
    }
}