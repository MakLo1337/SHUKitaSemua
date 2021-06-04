package com.shukitasemua.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Map;

import model.admin;

public class register extends AppCompatActivity {


    private TextInputLayout reg_username, reg_password;
    private Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);
        reg_button = findViewById(R.id.reg_button);

        reg_button.setEnabled(false);

        reg_username.getEditText().addTextChangedListener(regist);
        reg_password.getEditText().addTextChangedListener(regist);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = reg_username.getEditText().getText().toString().trim();
                String pass = reg_password.getEditText().getText().toString().trim();
                String level = "admin";
                admin baru = new admin(user,pass,level);
                daftar(baru);


            }
        });




    }
    private void daftar(admin baru){
        String url =  "http://192.168.1.6/progtech_SHUkitasemua/pengurus/CreateUser.php";
        RequestQueue myrequest = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Selamat Datang Pengurus Baru!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Gagal Mendaftar!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data2 = new HashMap<>();
                data2.put("username", baru.getUsername());
                data2.put("password", baru.getPassword());
                data2.put("level", baru.getLevel());


                return data2;
            }
        };
        myrequest.add(request);
    }
    private TextWatcher regist = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(reg_username.getEditText().length() < 16 && reg_password.getEditText().length() >= 4){
                reg_button.setEnabled(true);
                reg_username.setError("");
                reg_password.setError("");
            }
            else{
                reg_button.setEnabled(false);
                reg_username.setError("Nama harus dibawah 16 huruf");
                reg_password.setError("Password minimal 4 digit");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}