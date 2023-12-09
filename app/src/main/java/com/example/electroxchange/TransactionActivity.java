package com.example.electroxchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    private MyDBHandler myDB;
    ImageView cartImage, profile, home;
    TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        textViewEmail = findViewById(R.id.textView13);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        textViewEmail.setText(userEmail);
        myDB = new MyDBHandler(this);

        ListView transactionListView = findViewById(R.id.listTrans);
        int userId = getIntent().getIntExtra("USER_ID", -1);

        ArrayList<Transaction> transactionList = myDB.getTransactionsForUser(userId);
        TransactionAdapter adapter = new TransactionAdapter(this, transactionList);
        transactionListView.setAdapter(adapter);
        cartImage = findViewById(R.id.cartImage);
        profile = findViewById(R.id.imageView3);
        home = findViewById(R.id.imageView9);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, MainActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, Cart.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionActivity.this, profile.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDB != null) {
            myDB.close();
        }
    }
}
