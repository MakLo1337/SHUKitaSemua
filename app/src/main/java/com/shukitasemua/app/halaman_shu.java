package com.shukitasemua.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.SHU;
import model.anggota;
import model.dataAnggota;
import model.koperasi;


public class halaman_shu extends Fragment {

    private double simpananKoperasi = 0;
    private double penjualanKoperasi = 0;
    private ArrayList<SHU> SHUdata;
    private RecyclerView recyclerdataSHU;
    private dataSHUAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_halaman_shu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerdataSHU = view.findViewById(R.id.recyclerdataSHU);
        SHUdata = new ArrayList<SHU>();
        adapter = new dataSHUAdapter(SHUdata);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerdataSHU.setLayoutManager(manager);
        recyclerdataSHU.setAdapter(adapter);

        for (int i = 0; i < dataAnggota.dataanggota.size(); i++) {
            anggota hitung = dataAnggota.dataanggota.get(i);
            simpananKoperasi += hitung.getSimpanan();
            penjualanKoperasi += hitung.getPembelian();
        }

        String url2 = "http://158.140.167.137/progtech_SHUkitasemua/koperasi/ReadAllBarang.php";
        RequestQueue myQueue2 = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonKoperasi = response.getJSONArray("koperasi");
                            JSONObject objKoperasi = jsonKoperasi.getJSONObject(0);
                            for (int i = 0; i < dataAnggota.dataanggota.size(); i++) {
                                anggota hitungSHU = dataAnggota.dataanggota.get(i);
                                double jasamodal = (hitungSHU.getSimpanan() / simpananKoperasi) * (((objKoperasi.getDouble("jasamodal") / 100 ) * (objKoperasi.getDouble("shu"))));
                                double jasaanggota = (hitungSHU.getPembelian() / penjualanKoperasi) * (((objKoperasi.getDouble("jasaanggota") / 100 ) *(objKoperasi.getDouble("shu"))));
                                double jasalainya  = (objKoperasi.getDouble("lainlain") / 100) * objKoperasi.getDouble("shu");
                                double shu = jasamodal + jasaanggota;
                                String nama = hitungSHU.getNama();
                                SHU shu1 = new SHU(shu,nama);
                                SHUdata.add(shu1);
                            }
                            adapter.notifyDataSetChanged();
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

        myQueue2.add(request2);

    }

}