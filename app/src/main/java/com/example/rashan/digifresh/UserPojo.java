package com.example.rashan.digifresh;

import java.io.Serializable;

/**
 * Created by Rashan on 21-01-2017.
 */
public class UserPojo implements Serializable {

    String name;
    String email;
    String password;

    public UserPojo(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public UserPojo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
