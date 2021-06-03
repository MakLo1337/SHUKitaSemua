package com.shukitasemua.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.koperasi;

public class edit_koperasi extends AppCompatActivity {

    private TextInputLayout nama_id, shu_id, jm_id, ja_id, lainlain_id;
    private Button button_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_koperasi);
        getSupportActionBar().hide();

        nama_id = findViewById(R.id.nama_id);
        shu_id = findViewById(R.id.shu_id);
        jm_id = findViewById(R.id.jm_id);
        ja_id = findViewById(R.id.ja_id);
        lainlain_id = findViewById(R.id.lainlain_id);
        button_id = findViewById(R.id.button_id);

        button_id.setEnabled(false);
        button_id.setTextColor(Color.GRAY);

        nama_id.getEditText().addTextChangedListener(editKops);
        shu_id.getEditText().addTextChangedListener(editKops);
        jm_id.getEditText().addTextChangedListener(editKops);
        ja_id.getEditText().addTextChangedListener(editKops);
        lainlain_id.getEditText().addTextChangedListener(editKops);

        final LoadingDialog dialog = new LoadingDialog(edit_koperasi.this);

        String url3 = "http://158.140.167.137/progtech_SHUkitasemua/koperasi/ReadAllBarang.php";
        RequestQueue myQueue3 = Volley.newRequestQueue(getBaseContext());

        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonlihat = response.getJSONArray("koperasi");
                            JSONObject objlihat = jsonlihat.getJSONObject(0);

                                String datanama = objlihat.getString("nama");
                                double datashu = objlihat.getDouble("shu");
                                double datajasamodal = objlihat.getDouble("jasamodal");
                                double datajasaanggota = objlihat.getDouble("jasaanggota");
                                double datalainlain = objlihat.getDouble("lainlain");

                                nama_id.getEditText().setText(datanama);
                                shu_id.getEditText().setText( String.valueOf(datashu));
                                jm_id.getEditText().setText( String.valueOf(datajasamodal));
                                ja_id.getEditText().setText( String.valueOf(datajasaanggota));
                                lainlain_id.getEditText().setText(String.valueOf(datalainlain));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        myQueue3.add(request3);



        button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String nama = nama_id.getEditText().getText().toString().trim();
                double shu = Double.parseDouble(shu_id.getEditText().getText().toString().trim());
                double jasamodal = Double.parseDouble(jm_id.getEditText().getText().toString().trim());
                double jasaanggota = Double.parseDouble(ja_id.getEditText().getText().toString().trim());
                double lainlain = Double.parseDouble(lainlain_id.getEditText().getText().toString().trim());

                dialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismissDialog();
                    }
                },1000);


                koperasi kops = new koperasi(nama,shu,jasamodal,jasaanggota,lainlain);
                editkops(kops);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }

            private void editkops(koperasi kops) {
                String url2 =  "http://158.140.167.137/progtech_SHUkitasemua/koperasi/UpdateBarang.php";
                RequestQueue myRequest2 = Volley.newRequestQueue(getBaseContext());
                StringRequest request2 = new StringRequest(Request.Method.POST, url2,
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
                        data.put("id", String.valueOf(kops.getId(0)));
                        data.put("nama", kops.getNama());
                        data.put("shu", String.valueOf(kops.getShu()));
                        data.put("jasamodal", String.valueOf(kops.getJasamodal()));
                        data.put("jasaanggota", String.valueOf(kops.getJasaanggota()));
                        data.put("lainlain", String.valueOf(kops.getLainlain()));

                        return data;
                    }
                };
                myRequest2.add(request2);
            }
        });

    }

    private TextWatcher editKops = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean nameCheck = (nama_id.getEditText().length() <= 16);

            if(!nameCheck){
                button_id.setEnabled(false);
                button_id.setTextColor(Color.GRAY);
                nama_id.setError("Nama Tidak Boleh Lebih Dari 16 Huruf");
            } else if (nameCheck) {
                nama_id.setError("");
                if (jm_id.getEditText().length() < 1 | ja_id.getEditText().length() < 1 | lainlain_id.getEditText().length() < 1 | shu_id.getEditText().length() < 1){
                    button_id.setEnabled(false);
                    button_id.setTextColor(Color.GRAY);
                } else {
                    button_id.setEnabled(true);
                    button_id.setTextColor(Color.WHITE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}