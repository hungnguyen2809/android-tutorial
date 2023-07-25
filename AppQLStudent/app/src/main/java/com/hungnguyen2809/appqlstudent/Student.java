package com.hungnguyen2809.appqlstudent;

import java.io.Serializable;

public class Student implements Serializable {
    private String sbd;
    private String name;
    private double diemToan;
    private double diemLy;
    private double diemHoa;

    public Student(String sbd, String name, double diemToan, double diemLy, double diemHoa) {
        this.sbd = sbd;
        this.name = name;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
    }

    public double TongDiem(){
        return (this.diemToan + this.diemHoa + this.diemLy) ;
    }

    public String getSbd() {
        return sbd;
    }

    public void setSbd(String sbd) {
        this.sbd = sbd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(float diemToan) {
        this.diemToan = diemToan;
    }

    public double getDiemLy() {
        return diemLy;
    }

    public void setDiemLy(float diemLy) {
        this.diemLy = diemLy;
    }

    public double getDiemHoa() {
        return diemHoa;
    }

    public void setDiemHoa(float diemHoa) {
        this.diemHoa = diemHoa;
    }
}
