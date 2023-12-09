package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class tablet extends AppCompatActivity {
    ImageView profile, home, transaction, cart;
    TextView textViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        textViewEmail = findViewById(R.id.textView13);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        int userId = getIntent().getIntExtra("USER_ID", -1);
        textViewEmail.setText(userEmail);

        profile = findViewById(R.id.imageView3);
        home = findViewById(R.id.imageView9);
        cart = findViewById(R.id.cart);
        transaction = findViewById(R.id.imageView10);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tablet.this, profile.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tablet.this, MainActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tablet.this, Cart.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tablet.this, TransactionActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.list);

        MyDBHandler myDB = new MyDBHandler(this);
        ArrayList<Products> laptopItems = myDB.getProductsByCategory("tablet");

        ProductAdapter adapter = new ProductAdapter(this, laptopItems);
        listView.setAdapter(adapter);
    }
}