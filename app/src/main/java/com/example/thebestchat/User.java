package com.example.thebestchat;

public class User {
    public String email;
    public String password;
    public String name;

    User() {
        email=null;
        password=null;
        name=null;
    }

    User(String email, String password, String name) {
        this.email=email;
        this.password=password;
        this.name=name;

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
