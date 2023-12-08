package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PengaturanAkun extends AppCompatActivity {

    private EditText editTextNewEmail, editTextNewFullName, editTextNewPassword;
    private MyDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_akun);

        editTextNewEmail = findViewById(R.id.editTextText9);
        editTextNewFullName = findViewById(R.id.editTextText8);
        editTextNewPassword = findViewById(R.id.editTextTextPassword4);
        Button buttonUpdate = findViewById(R.id.button9);
        myDB = new MyDBHandler(this);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });
    }

    private void updateUserDetails() {
        String newEmail = editTextNewEmail.getText().toString().trim();
        String newFullName = editTextNewFullName.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();

        // Assuming you have a method in your MyDBHandler class to update user details
        boolean updated = myDB.updateUserDetails(newEmail, newFullName, newPassword);

        if (updated) {
            Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show();
            // You can also add further actions like reloading the user details or finishing this activity
        } else {
            Toast.makeText(this, "Failed to update details", Toast.LENGTH_SHORT).show();
        }
    }
}
