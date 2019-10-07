package com.example.rashan.digifresh;

import java.io.Serializable;

public class SubAreaPojo implements Serializable {

    String name;
    int subAreaCode;
    int placeCode;



    public SubAreaPojo() {
    }

    public SubAreaPojo(String name, int subAreaCode, int placeCode) {
        this.name = name;
        this.subAreaCode = subAreaCode;
        this.placeCode = placeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubAreaCode() {
        return subAreaCode;
    }

    public void setSubAreaCode(int subAreaCode) {
        this.subAreaCode = subAreaCode;
    }

    public int getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(int placeCode) {
        this.placeCode = placeCode;
    }
}
