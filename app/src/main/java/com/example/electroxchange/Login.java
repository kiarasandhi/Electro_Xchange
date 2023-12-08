package com.example.electroxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText editEmail, editPass;
    Button bLogin, bRegis;
    RequestQueue requestQueue;
    String URL = "http://192.168.0.104/mobapp/login.php"; // Replace with your PHP script URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editTextTextEmailAddress);
        editPass = findViewById(R.id.editTextTextPassword);
        bLogin = findViewById(R.id.button);
        bRegis = findViewById(R.id.button2);

        requestQueue = Volley.newRequestQueue(this);

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
                loginUser(email, pass);
            }
        });
    }

    private void loginUser(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();

                            if (message.equals("Login successful")) {
                                // Redirect to the main activity or any other activity upon successful login
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("USER_EMAIL", email);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "JSON Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
