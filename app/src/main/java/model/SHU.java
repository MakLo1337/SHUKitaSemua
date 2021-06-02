package model;

import android.os.Parcel;
import android.os.Parcelable;

public class SHU implements Parcelable {
    private double SHU;
    private String nama;

    public SHU() {
        this.SHU = 0;
        this.nama = "";
    }

    public SHU(double SHU, String nama) {
        this.SHU = SHU;
        this.nama = nama;
    }

    public double getSHU() {
        return SHU;
    }

    public void setSHU(double SHU) {
        this.SHU = SHU;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    protected SHU(Parcel in) {
        SHU = in.readDouble();
        nama = in.readString();
    }

    public static final Creator<SHU> CREATOR = new Creator<SHU>() {
        @Override
        public SHU createFromParcel(Parcel in) {
            return new SHU(in);
        }

        @Override
        public SHU[] newArray(int size) {
            return new SHU[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(SHU);
        dest.writeString(nama);
    }
}
