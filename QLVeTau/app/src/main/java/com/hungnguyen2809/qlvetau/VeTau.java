package com.hungnguyen2809.qlvetau;

import java.io.Serializable;
import java.lang.Comparable;

public class VeTau implements Serializable{
    private int ma;
    private String gaDi;
    private String gaDen;
    private long gia;
    private boolean status;

    public VeTau(int ma, String gaDi, String gaDen, long gia, boolean status) {
        this.ma = ma;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.gia = gia;
        this.status = status;
    }

    public VeTau(String gaDi, String gaDen, long gia, boolean status) {
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.gia = gia;
        this.status = status;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
