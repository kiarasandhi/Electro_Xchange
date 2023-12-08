package com.example.electroxchange;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView profile;
    TextView textViewEmail;
    ListView listView;
    MyDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewEmail = findViewById(R.id.textView13);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        int userId = getIntent().getIntExtra("USER_ID", -1);
        textViewEmail.setText(userEmail);
        profile = findViewById(R.id.imageView3);
        listView = findViewById(R.id.list);

        myDB = new MyDBHandler(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        // Fetch products for the current user from the database
        ArrayList<Product> productList = myDB.getProductsForUser(userId);

        // Create and set the adapter for the ListView
        ProductAdapter adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);
    }
}
