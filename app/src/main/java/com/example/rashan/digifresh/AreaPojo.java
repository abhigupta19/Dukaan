package com.example.rashan.digifresh;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rashan on 10-01-2017.
 */
public class AreaPojo implements Serializable {
    String name;
    int areaCode;
    List<SubAreaPojo> subAreaPojoList;

    public AreaPojo(String name, int areaCode, List<SubAreaPojo> subAreaPojoList) {
        this.name = name;
        this.areaCode = areaCode;
        this.subAreaPojoList = subAreaPojoList;
    }

    public AreaPojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public List<SubAreaPojo> getSubAreaPojoList() {
        return subAreaPojoList;
    }

    public void setSubAreaPojoList(List<SubAreaPojo> subAreaPojoList) {
        this.subAreaPojoList = subAreaPojoList;
    }
}
