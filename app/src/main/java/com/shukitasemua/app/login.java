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

//import model.admin;

public class login extends AppCompatActivity {

    private Button reg_button, login_button;
    private TextInputLayout login_user, login_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        login_button = findViewById(R.id.login_button);
        login_user = findViewById(R.id.login_user);
        login_pass = findViewById(R.id.login_pass);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = login_user.getEditText().getText().toString().trim();
                String pass = login_pass.getEditText().getText().toString().trim();

                if (!user.isEmpty() || !pass.isEmpty()) {
                    userlogin();
                } else {
                    login_user.setError("Masukkan Username Anda ");
                    login_pass.setError("Masukkan Password Anda");
                }
            }
        });

    }

    private void userlogin(){
        String url = "http://158.140.167.137/progtech_SHUkitasemua/pengurus/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Wrong username or password!" + response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", login_user.getEditText().getText().toString().trim());
                params.put("password", login_pass.getEditText().getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}