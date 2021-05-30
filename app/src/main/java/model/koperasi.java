package model;

import android.os.Parcel;
import android.os.Parcelable;

public class koperasi implements Parcelable {
    private int id;
    private String nama;
    private double shu, jasamodal , jasaanggota , lainlain;

    protected koperasi(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        shu = in.readDouble();
        jasamodal = in.readDouble();
        jasaanggota = in.readDouble();
        lainlain = in.readDouble();
    }

    public static final Creator<koperasi> CREATOR = new Creator<koperasi>() {
        @Override
        public koperasi createFromParcel(Parcel in) {
            return new koperasi(in);
        }

        @Override
        public koperasi[] newArray(int size) {
            return new koperasi[size];
        }
    };

    public koperasi( String nama, double shu, double jasamodal, double jasaanggota, double lainlain) {
        this.id = 0;
        this.nama = nama;
        this.shu = shu;
        this.jasamodal = jasamodal;
        this.jasaanggota = jasaanggota;
        this.lainlain = lainlain;
    }

    public int getId(int i) {
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

    public double getShu() {
        return shu;
    }

    public void setShu(double shu) {
        this.shu = shu;
    }

    public double getJasamodal() {
        return jasamodal;
    }

    public void setJasamodal(double jasamodal) {
        this.jasamodal = jasamodal;
    }

    public double getJasaanggota() {
        return jasaanggota;
    }

    public void setJasaanggota(double jasaanggota) {
        this.jasaanggota = jasaanggota;
    }

    public double getLainlain() {
        return lainlain;
    }

    public void setLainlain(double lainlain) {
        this.lainlain = lainlain;
    }

    public koperasi(int id, String nama, double shu, double jasamodal, double jasaanggota, double lainlain) {
        this.id = id;
        this.nama = nama;
        this.shu = shu;
        this.jasamodal = jasamodal;
        this.jasaanggota = jasaanggota;
        this.lainlain = lainlain;
    }
    public koperasi(){
        this.id = 0;
        this.nama = "";
        this.shu = 0;
        this.jasamodal = 0;
        this.jasaanggota = 0;
        this.lainlain = 0;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeDouble(shu);
        dest.writeDouble(jasamodal);
        dest.writeDouble(jasaanggota);
        dest.writeDouble(lainlain);
    }
}
