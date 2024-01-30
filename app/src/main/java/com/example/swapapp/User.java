package com.example.swapapp;

public class User {
    private String email;
    private String password;
    private String name;

    public User() {
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getemail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
