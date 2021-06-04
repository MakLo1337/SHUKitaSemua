package com.shukitasemua.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.admin;

public class login extends AppCompatActivity {

    private Button reg_button, login_button;
    private TextInputLayout login_user, login_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg_button = findViewById(R.id.login_reg);
        login_button = findViewById(R.id.login_button);
        login_user = findViewById(R.id.login_user);
        login_pass = findViewById(R.id.login_pass);


        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), register.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = login_user.getEditText().getText().toString().trim();
                String pass = login_pass.getEditText().getText().toString().trim();

                if (!user.isEmpty() || !pass.isEmpty()) {
                    masuk(user, pass);
                } else {
                    login_user.setError("Masukkan Username Anda ");
                    login_pass.setError("Masukkan Password Anda");
                }
//
//
//
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void masuk(String username, String password) {
        login_button.setVisibility(View.GONE);
        String url = "http://192.168.1.6/progtech_SHUkitasemua/pengurus/CreateUser.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sukses = jsonObject.getString("sukses");
                    JSONArray jsonArray = jsonObject.getJSONArray("admin");

                    if (sukses.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String username = object.getString("username").trim();
                            String password = object.getString("password").trim();
                            String level = "admin";
                            Toast.makeText(getBaseContext(), "berhasil masuk", Toast.LENGTH_SHORT).show();

                            admin ad = new admin(username , password, level);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "GAGAL Masuk!", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "ERROR!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data2 = new HashMap<>();
                data2.put("username" , username);
                data2.put("password" , password);


                return  data2;
            }
        };
    requestQueue.add(stringRequest);
    }
}