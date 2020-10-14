package com.lember.sqlite.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Contact implements Serializable {
    private int id;
    private String first;
    private String last;
    private String address;
    private Code code;
    private String phone;
    private String email;
    private String date;

    ///empty constructor
    public Contact() {
    }
    ///constructor without id

    public Contact(String first, String last, String address, Code code, String phone, String email, String date) {
        this.first = first;
        this.last = last;
        this.address = address;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.date = date;
    }
    ///constructor with all

    public Contact(int id, String first, String last, String address, Code code, String phone, String email, String date) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.address = address;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @NonNull
    @Override
    public String toString(){
        return first + " " + last;
    }
}
