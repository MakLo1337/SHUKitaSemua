package model;

import android.os.Parcel;
import android.os.Parcelable;

public class anggota implements Parcelable {
    private int id;
    private String nama;
    private double simpanan, pembelian , jumlah;

    public anggota(){
        this.id = 0;
        this.nama = "";
        this.simpanan = 0;
        this.pembelian = 0;
        this.jumlah = 0;

    }
    public anggota(int id, String nama, double simpanan, double pembelian, double jumlah){
        this.id = id;
        this.nama = nama;
        this.simpanan = simpanan;
        this.pembelian = pembelian;
        this.jumlah = jumlah;
    }

    protected anggota(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        simpanan = in.readDouble();
        pembelian = in.readDouble();
        jumlah = in.readDouble();
    }

    public static final Creator<anggota> CREATOR = new Creator<anggota>() {
        @Override
        public anggota createFromParcel(Parcel in) {
            return new anggota(in);
        }

        @Override
        public anggota[] newArray(int size) {
            return new anggota[size];
        }
    };

    public anggota(String nama, double simpanan, double pembelian, double jumlah) {
        this.id = 0;
        this.nama = nama;
        this.simpanan = simpanan;
        this.pembelian = pembelian;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getSimpanan() {
        return simpanan;
    }

    public void setSimpanan(double simpanan) {
        this.simpanan = simpanan;
    }

    public double getPembelian() {
        return pembelian;
    }

    public void setPembelian(double pembelian) {
        this.pembelian = pembelian;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeDouble(simpanan);
        dest.writeDouble(pembelian);
        dest.writeDouble(jumlah);
    }
}
