package com.shukitasemua.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.anggota;


public class RVAAdapter extends RecyclerView.Adapter<RVAAdapter.ViewHolder>{
    private ArrayList<anggota> listanggota;
    protected OnCardListener cardListener;


    public RVAAdapter(ArrayList<anggota> listanggota, OnCardListener onCardListener) {
        this.cardListener = onCardListener;
        this.listanggota = listanggota;


    }

    @NonNull
    @Override
    public RVAAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.data_anggota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAAdapter.ViewHolder holder, int position) {
//        final anggota member = listanggota.get(position);
        holder.main_nama.setText(listanggota.get(position).getNama());
        holder.main_simpanan.setText(String.valueOf(listanggota.get(position).getSimpanan()));
        holder.main_pembelian.setText(String.valueOf(listanggota.get(position).getPembelian()));


    }

    @Override
    public int getItemCount() {
        return listanggota.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView main_nama , main_simpanan, main_pembelian;
        private Button delete_anggota , edit_anggota;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_nama = itemView.findViewById(R.id.main_nama);
            main_simpanan = itemView.findViewById(R.id.main_simpanan);
            main_pembelian = itemView.findViewById(R.id.main_pembelian);
            delete_anggota = itemView.findViewById(R.id.delete_anggota);
            edit_anggota = itemView.findViewById(R.id.edit_anggota);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardListener.OnCardClick(getAdapterPosition());
                }
            }


            );
//
        }
    }

}
