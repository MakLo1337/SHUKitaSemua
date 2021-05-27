package com.shukitasemua.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

import model.anggota;


public class add_member extends Fragment {


private TextInputLayout tambah_nama, tambah_simpanan, tambah_pembelian;
private Button tambah_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_member, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tambah_nama = view.findViewById(R.id.tambah_nama);
        tambah_simpanan = view.findViewById(R.id.tambah_simpanan);
        tambah_pembelian = view.findViewById(R.id.tambah_pembelian);
        tambah_button = view.findViewById(R.id.tambah_button);

        tambah_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = tambah_nama.getEditText().getText().toString().trim();
                double simpanan = Double.parseDouble(tambah_simpanan.getEditText().getText().toString().trim());
                double pembelian = Double.parseDouble(tambah_pembelian.getEditText().getText().toString().trim());
                double jumlah = simpanan + pembelian;

                anggota temp = new anggota(nama,simpanan,pembelian,jumlah);
                postData(temp);
            }

            private void postData(anggota temp) {
                String url =  "http://192.168.100.4/progtech_SHUkitasemua/CreateBarang.php";
                RequestQueue myRequest = Volley.newRequestQueue(getContext());

                StringRequest request = new StringRequest(Request.Method.POST, url,
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
                        data.put("nama", temp.getNama());
                        data.put("simpanan", String.valueOf(temp.getSimpanan()));
                        data.put("pembelian", String.valueOf(temp.getPembelian()));
                        data.put("jumlah", String.valueOf(temp.getJumlah()));

                        return data;
                    }
                };
                myRequest.add(request);
            }
        });

    }
}