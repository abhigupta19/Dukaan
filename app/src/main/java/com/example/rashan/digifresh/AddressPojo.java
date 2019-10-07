package com.example.rashan.digifresh;

/**
 * Created by Rashan on 18-10-2016.
 */
public class AddressPojo {
    String address1;
    String address2;
   String subarea,area;
    int subAreaCode,placeCode,areaCode;
    int id;
    String name;



    public AddressPojo(int id, String name, String address1, String address2, String subarea, String area,int subAreaCode,int placeCode,int areaCode) {
        this.address1 = address1;
        this.address2=address2;
        this.name = name;
        this.id = id;
        this.area = area;
        this.subarea = subarea;
        this.subAreaCode=subAreaCode;
        this.placeCode=placeCode;
        this.areaCode=areaCode;
    }

    public AddressPojo() {
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
