package com.shukitasemua.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.anggota;

public class edit_anggota extends AppCompatActivity {

    private TextInputLayout edit_nama, edit_simpanan, edit_pembelian;
    private Button edit_button;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anggota);
        getSupportActionBar().hide();
        initview();
        getData();


        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String nama = edit_nama.getEditText().getText().toString().trim();
            double simpanan = Double.parseDouble(edit_simpanan.getEditText().getText().toString().trim());
            double pembelian = Double.parseDouble(edit_pembelian.getEditText().getText().toString().trim());
            double jumlah = simpanan + pembelian;
                anggota temp2 = new anggota(id,nama,simpanan,pembelian,jumlah);
                editanggota(temp2);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            private void editanggota(anggota temp2){
                String url5 =  "http://192.168.1.6/progtech_SHUkitasemua/UpdateBarang.php";
                RequestQueue myRequest2 = Volley.newRequestQueue(getBaseContext());
                StringRequest request2 = new StringRequest(Request.Method.POST, url5,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("id", String.valueOf(temp2.getId()));
                        data.put("nama", temp2.getNama());
                        data.put("simpanan", String.valueOf(temp2.getSimpanan()));
                        data.put("pembelian", String.valueOf(temp2.getPembelian()));
                        data.put("jumlah", String.valueOf(temp2.getJumlah()));

                        return data;
                    }
                };
                myRequest2.add(request2);
            }
        });
    }

    private void initview(){
        edit_nama = findViewById(R.id.edit_nama);
        edit_simpanan= findViewById(R.id.edit_simpanan);
        edit_pembelian = findViewById(R.id.edit_pembelian);
        edit_button = findViewById(R.id.edit_button);

        Intent intent = getIntent();
         id = intent.getIntExtra("id", 0);
    }

    private void getData(){
        String url4 =  "http://192.168.1.6/progtech_SHUkitasemua/ReadBarangByID.php";
        RequestQueue myQueue = Volley.newRequestQueue(this);
        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url4, parameter,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dbanggota = response.getJSONObject("anggota");
                   String nama = dbanggota.getString("nama");
                   double simpanan = dbanggota.getDouble("simpanan");
                   double pembelian = dbanggota.getDouble("pembelian");

                   edit_nama.getEditText().setText(nama);
                   edit_simpanan.getEditText().setText(String.valueOf(simpanan));
                   edit_pembelian.getEditText().setText(String.valueOf(pembelian));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        myQueue.add(request);
    }
}