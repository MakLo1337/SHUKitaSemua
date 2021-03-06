package com.shukitasemua.app;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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

        tambah_nama = view.findViewById(R.id.Tambah_nama);
        tambah_simpanan = view.findViewById(R.id.Tambah_simpanan);
        tambah_pembelian = view.findViewById(R.id.Tambah_pembelian);
        tambah_button = view.findViewById(R.id.Tambah_button);
        tambah_button.setEnabled(false);
        tambah_button.setTextColor(Color.GRAY);

        tambah_nama.getEditText().addTextChangedListener(addMember);
        tambah_simpanan.getEditText().addTextChangedListener(addMember);
        tambah_pembelian.getEditText().addTextChangedListener(addMember);

        tambah_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = tambah_nama.getEditText().getText().toString().trim();
                double simpanan = Double.parseDouble(tambah_simpanan.getEditText().getText().toString().trim());
                double pembelian = Double.parseDouble(tambah_pembelian.getEditText().getText().toString().trim());
                double jumlah = simpanan + pembelian;

                anggota temp = new anggota(nama,simpanan,pembelian,jumlah);
                postData(temp);
                tambah_nama.getEditText().setText("");
                tambah_simpanan.getEditText().setText("");
                tambah_pembelian.getEditText().setText("");

                Toast.makeText(getActivity(),"Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show();

            }

            private void postData(anggota temp) {
                //158.140.167.137 <-- IP server
                String url =  "http://158.140.167.137/progtech_SHUkitasemua/CreateBarang.php";
                RequestQueue myRequest = Volley.newRequestQueue(getActivity());

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

    private TextWatcher addMember = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean nameCheck = (tambah_nama.getEditText().length() <= 16);

            if(!nameCheck){
                tambah_button.setEnabled(false);
                tambah_button.setTextColor(Color.GRAY);
                tambah_nama.setError("Nama Tidak Boleh Lebih Dari 16 Huruf");
            } else if (nameCheck) {
                tambah_nama.setError("");
                if (tambah_simpanan.getEditText().length() < 1 | tambah_pembelian.getEditText().length() < 1){
                    tambah_button.setEnabled(false);
                    tambah_button.setTextColor(Color.GRAY);
                } else {
                    tambah_button.setEnabled(true);
                    tambah_button.setTextColor(Color.WHITE);
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}