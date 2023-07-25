package com.hungnguyen2809.qlnhahang;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String ma;
    private String name;
    private double point;
    private String address;

    public Restaurant(String ma, String name, double point, String address) {
        this.ma = ma;
        this.name = name;
        this.point = point;
        this.address = address;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
