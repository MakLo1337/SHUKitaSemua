package com.shukitasemua.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import model.anggota;
import model.koperasi;


public class HomeFragment extends Fragment implements OnCardListener {


private Button edit_kops;
private RecyclerView recyclerView_recyclerView;
private ArrayList<anggota> dataanggota;
private RVAAdapter adapter;
private TextView namaKoperasi, count_shu, count_modal, count_anggota, count_lainlain;
private ArrayList<koperasi> kops;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        //komponen


        return view;



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_kops = view.findViewById(R.id.edit_kops);
        namaKoperasi = view.findViewById(R.id.namaKoperasi);
        count_shu = view.findViewById(R.id.count_shu);
        count_modal = view.findViewById(R.id.count_modal);
        count_anggota = view.findViewById(R.id.count_anggota);
        count_lainlain = view.findViewById(R.id.count_lainlain);


        recyclerView_recyclerView = view.findViewById(R.id.recyclerView_recyclerView);
        dataanggota = new ArrayList<anggota>();
        adapter = new RVAAdapter(dataanggota, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView_recyclerView.setLayoutManager(manager);
        recyclerView_recyclerView.setAdapter(adapter);

        String url = "http://158.140.167.137/progtech_SHUkitasemua/ReadAllBarang.php";
        RequestQueue myQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonAnggota = response.getJSONArray("anggota");
                            for (int i = 0; i < jsonAnggota.length(); i++) {
                                JSONObject objAnggota = jsonAnggota.getJSONObject(i);
                                anggota anggotabaru = new anggota();
//                                anggotabaru.setId(objAnggota.getInt("id"));
                                anggotabaru.setNama(objAnggota.getString("nama"));
                                anggotabaru.setSimpanan(objAnggota.getDouble("simpanan"));
                                anggotabaru.setPembelian(objAnggota.getDouble("pembelian"));
                                anggotabaru.setJumlah(objAnggota.getDouble("jumlah"));
                                dataanggota.add(anggotabaru);

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

        myQueue.add(request);

        String url2 = "http://158.140.167.137/progtech_SHUkitasemua/koperasi/ReadAllBarang.php";
        RequestQueue myQueue2 = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonKoperasi = response.getJSONArray("koperasi");
                            for (int i = 0; i < jsonKoperasi.length(); i++) {
                                JSONObject objKoperasi = jsonKoperasi.getJSONObject(i);
                                koperasi datakoperasi = new koperasi();
//                                anggotabaru.setId(objAnggota.getInt("id"));
                                String nama = objKoperasi.getString("nama");
                                double shu = objKoperasi.getDouble("shu");
                                double jasamodal = objKoperasi.getDouble("jasamodal");
                                double jasaanggota = objKoperasi.getDouble("jasaanggota");
                                double lainlain = objKoperasi.getDouble("lainlain");
//                                datakoperasi.setNama(nama);
//                                datakoperasi.setShu(shu);
//                                datakoperasi.setJasamodal(jasamodal);
//                                datakoperasi.setJasaanggota(jasaanggota);
//                                datakoperasi.setLainlain(lainlain);

                                namaKoperasi.setText(nama);
                                count_shu.setText( "Rp."+(String.valueOf(shu)));
                                count_modal.setText(String.valueOf(jasamodal) + " %");
                                count_anggota.setText(String.valueOf(jasaanggota) + " %");
                                count_lainlain.setText(String.valueOf(lainlain) + " %");

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

        edit_kops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), edit_koperasi.class);
                startActivity(intent);

            }
        });


    }


    @Override
    public void OnCardClick(int position) {
        int id = dataanggota.get(position).getId();


    }
}