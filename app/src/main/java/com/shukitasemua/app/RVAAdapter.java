package com.shukitasemua.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import model.anggota;


public class RVAAdapter extends RecyclerView.Adapter<RVAAdapter.ViewHolder>{
    private ArrayList<anggota> listanggota;


    public RVAAdapter(ArrayList<anggota> listanggota) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_nama = itemView.findViewById(R.id.main_nama);
            main_simpanan = itemView.findViewById(R.id.main_simpanan);
            main_pembelian = itemView.findViewById(R.id.main_pembelian);
        }
    }
}
