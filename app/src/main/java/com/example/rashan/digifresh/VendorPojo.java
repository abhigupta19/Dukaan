package com.example.rashan.digifresh;

/**
 * Created by Mahendroo on 15-07-2016.
 */
public class VendorPojo {
    String name, pic, gender;
    int id, category1, category2, category3, category4;

    public VendorPojo(String name, String pic, String gender, int id, int category1, int category2, int category3, int category4) {
        this.name = name;
        this.pic = pic;
        this.gender = gender;
        this.id = id;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
    }

    public VendorPojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory1() {
        return category1;
    }

    public void setCategory1(int category1) {
        this.category1 = category1;
    }

    public int getCategory2() {
        return category2;
    }

    public void setCategory2(int category2) {
        this.category2 = category2;
    }

    public int getCategory3() {
        return category3;
    }

    public void setCategory3(int category3) {
        this.category3 = category3;
    }

    public int getCategory4() {
        return category4;
    }

    public void setCategory4(int category4) {
        this.category4 = category4;
    }
}
