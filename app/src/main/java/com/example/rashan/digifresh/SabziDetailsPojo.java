package com.example.rashan.digifresh;

/**
 * Created by Mahendroo on 09-07-2016.
 */
public class SabziDetailsPojo {
    int id, category;
    float cost, weight;
    String name, pic, hinglish;

    public SabziDetailsPojo(int id, int category, float cost, float weight, String name, String pic, String hinglish) {
        this.id = id;
        this.category = category;
        this.cost = cost;
        this.weight = weight;
        this.name = name;
        this.pic = pic;
        this.hinglish = hinglish;
    }


    public SabziDetailsPojo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getHinglish() {
        return hinglish;
    }

    public void setHinglish(String hinglish) {
        this.hinglish = hinglish;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getWeight() {
        weight = Math.round(100 * weight) / 100.0f;
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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
}
