package com.shukitasemua.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.anggota;
import model.dataAnggota;
import model.koperasi;


public class HomeFragment extends Fragment implements EditListener, DeleteListener {


private Button edit_kops, delete_anggota, edit_anggota;
private RecyclerView recyclerView_recyclerView;
//private ArrayList<anggota> dataanggota;
private RVAAdapter adapter;
private TextView namaKoperasi, count_shu, count_modal, count_anggota, count_lainlain;
private ArrayList<koperasi> kops;
private DecimalFormat df = new DecimalFormat("###,###.###");


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
        df.setMaximumFractionDigits(0);


        edit_kops = view.findViewById(R.id.edit_kops);
        namaKoperasi = view.findViewById(R.id.namaKoperasi);
        count_shu = view.findViewById(R.id.count_shu);
        count_modal = view.findViewById(R.id.count_modal);
        count_anggota = view.findViewById(R.id.count_anggota);
        count_lainlain = view.findViewById(R.id.count_lainlain);


        recyclerView_recyclerView = view.findViewById(R.id.recyclerView_recyclerView);
//        dataanggota = new ArrayList<anggota>();
        dataAnggota.dataanggota = new ArrayList<anggota>();
        adapter = new RVAAdapter(dataAnggota.dataanggota, this, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView_recyclerView.setLayoutManager(manager);
        recyclerView_recyclerView.setAdapter(adapter);
        //158.140.167.137 <-- IP Server
        String url = "http://158.140.167.137/progtech_SHUkitasemua/ReadAllBarang.php";
        RequestQueue myQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonAnggota = response.getJSONArray("anggota");
                            dataAnggota.dataanggota.clear();
                            for (int i = 0; i < jsonAnggota.length(); i++) {
                                JSONObject objAnggota = jsonAnggota.getJSONObject(i);
                                anggota anggotabaru = new anggota();
                                anggotabaru.setId(objAnggota.getInt("id"));
                                anggotabaru.setNama(objAnggota.getString("nama"));
                                anggotabaru.setSimpanan(objAnggota.getDouble("simpanan"));
                                anggotabaru.setPembelian(objAnggota.getDouble("pembelian"));
                                anggotabaru.setJumlah(objAnggota.getDouble("jumlah"));
                                dataAnggota.dataanggota.add(anggotabaru);

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

                                JSONObject objKoperasi = jsonKoperasi.getJSONObject(0);
                                koperasi datakoperasi = new koperasi();

                                String nama = objKoperasi.getString("nama");
                                double shu = objKoperasi.getDouble("shu");
                                double jasamodal = objKoperasi.getDouble("jasamodal");
                                double jasaanggota = objKoperasi.getDouble("jasaanggota");
                                double lainlain = objKoperasi.getDouble("lainlain");


                                namaKoperasi.setText(nama);
                                count_shu.setText( "Rp."+(String.valueOf(df.format(shu))));
                                count_modal.setText(String.valueOf(df.format(jasamodal)) + " %");
                                count_anggota.setText(String.valueOf(df.format(jasaanggota)) + " %");
                                count_lainlain.setText(String.valueOf(df.format(lainlain)) + " %");


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
    public void OnEdit(int position) {
        int id = dataAnggota.dataanggota.get(position).getId();
        Intent intent = new Intent(getActivity(), edit_anggota.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void OnDelete(int position) {
        int id = dataAnggota.dataanggota.get(position).getId();

        String urldelete = "http://158.140.167.137/progtech_SHUkitasemua/Delete.php/" + id;

        StringRequest postRequest = new StringRequest(Request.Method.POST, urldelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int i = jsonObject.getInt("id");
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getActivity(),"Data Dihapus",Toast.LENGTH_LONG).show();
                        FragmentTransaction t = getFragmentManager().beginTransaction();
                        t.replace(R.id.frameLayout, new HomeFragment());
                        t.commit();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Data gagal dihapus",Toast.LENGTH_LONG).show();
            }

        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("id", String.valueOf(id));
                return data;

            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(postRequest);



    }




}