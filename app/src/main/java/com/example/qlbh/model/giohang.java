package com.example.qlbh.model;

public class giohang {
    public int id;
    public String tensp;
    public long gia;
    public String hinhanh;
    public int soluong;

    public giohang(int id, String tensp, long gia, String hinhanh, int soluong) {
        this.id = id;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
