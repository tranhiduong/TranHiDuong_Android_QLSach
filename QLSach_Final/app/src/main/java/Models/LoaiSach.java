package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoaiSach implements Serializable {
    private String maloai;
    private String tenloai;

    public LoaiSach() {
    }

    public LoaiSach(String maloai, String tenloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;

    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }


    @Override
    public String toString() {
        return tenloai ;
    }

    private List<Sach> dssach= new ArrayList<>();

    public List<Sach> getDssach() {
        return dssach;
    }

    public void setDssach(List<Sach> dssach) {
        this.dssach = dssach;
    }



}
