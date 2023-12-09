package com.example.electroxchange;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView profile, cartImage, trans, laptopIm, tabletIm, phoneIm, allIm;
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

        cartImage = findViewById(R.id.cartImage);
        profile = findViewById(R.id.imageView3);
        trans = findViewById(R.id.imageView10);
        laptopIm = findViewById(R.id.imageView8);
        tabletIm = findViewById(R.id.imageView7);
        phoneIm = findViewById(R.id.imageView5);
        allIm = findViewById(R.id.imageView4);

        listView = findViewById(R.id.list);

        laptopIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, laptop.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        tabletIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tablet.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        phoneIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, phone.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        allIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, All.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        myDB = new MyDBHandler(this);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cart.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        ArrayList<Products> productList = myDB.getAllProducts();

        ProductAdapter adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Products selectedProduct = (Products) parent.getItemAtPosition(position);
                showAddToCartDialog(selectedProduct);
                return true;
            }
        });
    }
    private void showAddToCartDialog(final Products product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add to Cart")
                .setMessage("Do you want to add this item to your cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addToCart(product);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
    private void addToCart(Products product) {
        int userId = getIntent().getIntExtra("USER_ID", -1);

        int productId = product.getProductId();
        String productName = product.getProductName();
        String category = product.getCategory();
        String description = product.getDescription();
        int quantity = product.getQuantity();
        double price = product.getPrice();

        long result = myDB.addToCart(userId, productId, productName, category, description, quantity, price);

        if (result != -1) {
            Toast.makeText(MainActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
        }
    }
}
