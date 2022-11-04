package Models;

import android.graphics.Bitmap;

import java.io.Serializable;


public class Sach implements Serializable {
    public String masach;
    private String tensach;
    private String tacgia;
    private String nhaxuatban;
    private byte[] hinhanh;

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Sach() {

    }

    public Sach(String masach, String tensach, String tacgia, String nhaxuatban) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.nhaxuatban = nhaxuatban;
    }

    public Sach(String masach, String tensach, String tacgia, String nhaxuatban, LoaiSach loaisach) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.nhaxuatban = nhaxuatban;
        this.loaisach = loaisach;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }


    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getNhaxuatban() {
        return nhaxuatban;
    }

    public void setNhaxuatban(String nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    @Override
    public String toString() {
        return  masach +  tensach  + tacgia +  nhaxuatban +  loaisach ;
    }

    private LoaiSach loaisach;

    public LoaiSach getLoaisach() {
        return loaisach;
    }

    public void setLoaisach(LoaiSach loaisach) {
        this.loaisach = loaisach;
    }
}
