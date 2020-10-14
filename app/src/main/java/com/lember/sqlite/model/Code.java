package com.lember.sqlite.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Code implements Serializable{
    private String code;
    private String country;

    public Code(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @NonNull
    @Override
    public String toString(){
        return getCode() + " " + getCountry();
    }
}

