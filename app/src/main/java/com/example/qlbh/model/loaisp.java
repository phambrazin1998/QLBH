package com.example.qlbh.model;

public class loaisp {
    public int id;
    public  String tenloaisp;
    public  String hinhloaisp;
    public loaisp(int id,String tenloaisp,String hinhloaisp)
    {
        this.tenloaisp=tenloaisp;
        this.id=id;
        this.hinhloaisp=hinhloaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhloaisp() {
        return hinhloaisp;
    }

    public void setHinhloaisp(String hinhloaisp) {
        this.hinhloaisp = hinhloaisp;
    }

}
