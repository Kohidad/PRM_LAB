package com.example.tuantase172217_petest2.Model;

public class Student {
    private int ID;
    private String name;
    private String date;
    private String gender;
    private String email;
    private String address;
    private int idMajor;

    public Student(int ID, String name, String date, String email, String gender, String address, int idMajor) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.idMajor = idMajor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(int idMajor) {
        this.idMajor = idMajor;
    }
}
