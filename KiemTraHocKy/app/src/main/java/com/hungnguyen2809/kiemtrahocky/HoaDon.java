package com.hungnguyen2809.kiemtrahocky;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int ma;
    private String name;
    private int soPhong;
    private double donGia;
    private int soNgayO;

    public HoaDon(int ma, String name, int soPhong, double donGia, int soNgayO) {
        this.ma = ma;
        this.name = name;
        this.soPhong = soPhong;
        this.donGia = donGia;
        this.soNgayO = soNgayO;
    }

    public HoaDon(String name, int soPhong, double donGia, int soNgayO) {
        this.name = name;
        this.soPhong = soPhong;
        this.donGia = donGia;
        this.soNgayO = soNgayO;
    }

    public int TongTien(){
        return (int)(this.soNgayO * this.donGia);
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoNgayO() {
        return soNgayO;
    }

    public void setSoNgayO(int soNgayO) {
        this.soNgayO = soNgayO;
    }
}
