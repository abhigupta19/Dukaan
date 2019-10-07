package com.example.rashan.digifresh;

/**
 * Created by Mahendroo on 23-07-2016.
 */
public class PreviousSubOrderPojo {

    int id;
    String name;
    float qty;
    float price;
    int sid;

    public PreviousSubOrderPojo(int id, int sid,String name, float qty, float price) {
        this.id = id;
        this.sid=sid;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public PreviousSubOrderPojo() {
    }


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
