package com.example.rashan.digifresh;

import java.util.Date;

/**
 * Created by Mahendroo on 18-07-2016.
 */
public class PreviousOrderPojo {
    long id;
    float price;
    Date date;
   // boolean status;
    int status;
    int rating;
    String pic;

    public PreviousOrderPojo(long id, float price, Date date, int status, int rating, String pic) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.status = status;
        this.rating = rating;
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public PreviousOrderPojo() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
