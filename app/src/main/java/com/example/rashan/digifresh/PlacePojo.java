package com.example.rashan.digifresh;

import java.io.Serializable;

/**
 * Created by Rashan on 29-12-2016.
 */
public class PlacePojo implements Serializable {

    String name;
    int code;
    int placeCode;
int areaCode;
    public PlacePojo(String name, int code,int placeCode,int areaCode) {
        this.name = name;
        this.code = code;
        this.placeCode=placeCode;
        this.areaCode=areaCode;
    }

    public PlacePojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(int placeCode) {
        this.placeCode = placeCode;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }


}

