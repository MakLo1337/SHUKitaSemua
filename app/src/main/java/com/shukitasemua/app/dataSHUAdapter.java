package com.shukitasemua.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;

import model.SHU;

public class dataSHUAdapter extends RecyclerView.Adapter<dataSHUAdapter.ViewHolder> {
    private ArrayList<SHU> dataSHU;
    private DecimalFormat df = new DecimalFormat("###,###.###");

    public dataSHUAdapter (ArrayList<SHU> dataSHU){
        this.dataSHU = dataSHU;
    }
    @NonNull
    @Override
    public dataSHUAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.data_shu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dataSHUAdapter.ViewHolder holder, int position) {
        df.setMaximumFractionDigits(0);
        holder.main_nama.setText(dataSHU.get(position).getNama());
        holder.main_shu.setText("Rp."+String.valueOf(df.format(dataSHU.get(position).getSHU())));
    }

    @Override
    public int getItemCount() {
        return dataSHU.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView main_shu, main_nama;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_nama = itemView.findViewById(R.id.main_nama);
            main_shu = itemView.findViewById(R.id.main_shu);
        }
    }
}
