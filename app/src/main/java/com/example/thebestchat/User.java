package com.example.thebestchat;

public class User {
    public String email;
    public String password;
    public String name;
    public String uId=null;

    User() {
        email="hello";
        password="hello";
        name="okay";
    }

    User(String email, String password, String name, String uId) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.uId=uId;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return name;
    }

    public String getuId() {
        return uId;
    }


}
