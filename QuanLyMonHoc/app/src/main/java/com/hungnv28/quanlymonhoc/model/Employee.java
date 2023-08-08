package com.hungnv28.quanlymonhoc.model;

public class Employee {
    private int id;
    private String code;
    private String name;
    private String nameDep;

    public Employee(int id, String code, String name, String nameDep) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.nameDep = nameDep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDep() {
        return nameDep;
    }

    public void setNameDep(String nameDep) {
        this.nameDep = nameDep;
    }
}

