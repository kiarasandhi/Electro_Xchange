package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    ImageView profile, home, transaction;
    TextView textViewEmail;
    ListView listView;
    MyDBHandler myDB;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textViewEmail = findViewById(R.id.textView13);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        textViewEmail.setText(userEmail);

        listView = findViewById(R.id.viewCart);
        myDB = new MyDBHandler(this);

        userId = getIntent().getIntExtra("USER_ID", -1);
        ArrayList<Products> cartItems = myDB.getCartItemsForUser(userId);

        CartProductAdapter adapter = new CartProductAdapter(this, cartItems);
        listView.setAdapter(adapter);

        profile = findViewById(R.id.imageView3);
        home = findViewById(R.id.imageView9);
        transaction = findViewById(R.id.imageView10);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, MainActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, profile.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, TransactionActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        Button checkoutButton = findViewById(R.id.button14);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCheckout();
            }
        });
    }

    private void performCheckout() {
        double totalAmount = myDB.calculateTotalCartAmount(userId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        long transactionId = myDB.addTransaction(userId, currentDateAndTime, totalAmount);

        if (transactionId != -1) {
            boolean clearedCart = myDB.clearCart(userId);

            if (clearedCart) {
                Toast.makeText(Cart.this, "Checkout successful", Toast.LENGTH_SHORT).show();

                ArrayList<Products> cartItems = myDB.getCartItemsForUser(userId);
                for (Products item : cartItems) {
                    boolean removed = myDB.removeProductItem(item.getProductId());
                    if (!removed) {
                        Toast.makeText(Cart.this, "Failed to remove product from inventory", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                CartProductAdapter adapter = new CartProductAdapter(this, new ArrayList<Products>());
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(Cart.this, "Failed to clear cart", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Cart.this, "Failed to perform checkout", Toast.LENGTH_SHORT).show();
        }
    }
}
