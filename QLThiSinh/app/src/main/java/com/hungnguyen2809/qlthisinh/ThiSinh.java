package com.hungnguyen2809.qlthisinh;

import java.io.Serializable;

public class ThiSinh implements Serializable {
    private String SBD;
    private String Name;
    private double Toan;
    private double Ly;
    private double Hoa;

    public ThiSinh(String SBD, String name, double toan, double ly, double hoa) {
        this.SBD = SBD;
        Name = name;
        Toan = toan;
        Ly = ly;
        Hoa = hoa;
    }

    public double TongDiem(){
        return this.Toan + this.Hoa + this.Ly;
    }

    public String getSBD() {
        return SBD;
    }

    public void setSBD(String SBD) {
        this.SBD = SBD;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getToan() {
        return Toan;
    }

    public void setToan(float toan) {
        Toan = toan;
    }

    public double getLy() {
        return Ly;
    }

    public void setLy(float ly) {
        Ly = ly;
    }

    public double getHoa() {
        return Hoa;
    }

    public void setHoa(float hoa) {
        Hoa = hoa;
    }
}
