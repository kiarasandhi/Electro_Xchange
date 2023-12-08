package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    EditText editProductName, editCategory, editDescription, editQuantity, editPrice;
    Button buttonAddProduct;
    MyDBHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editProductName = findViewById(R.id.editTextText4);
        editCategory = findViewById(R.id.editTextText5);
        editDescription = findViewById(R.id.editTextText7);
        editQuantity = findViewById(R.id.editTextNumber);
        editPrice = findViewById(R.id.editTextText6);
        buttonAddProduct = findViewById(R.id.button8);

        myDB = new MyDBHandler(this);
        int currentUserId = getIntent().getIntExtra("USER_ID", -1);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name = editProductName.getText().toString();
                String category = editCategory.getText().toString();
                String description = editDescription.getText().toString();
                int quantity = Integer.parseInt(editQuantity.getText().toString());
                double price = Double.parseDouble(editPrice.getText().toString());

                long result = myDB.addProduct(currentUserId, product_name, category, description, quantity, price);

                if (result != -1) {
                    Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Intent intent = new Intent(AddProduct.this, profile.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddProduct.this, "Failed to Add Product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        editProductName.setText("");
        editCategory.setText("");
        editDescription.setText("");
        editQuantity.setText("");
        editPrice.setText("");
    }
}
