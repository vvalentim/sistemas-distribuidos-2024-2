package com.vvalentim.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String name;
    private String username;
    private String password;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @JsonProperty("nome")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ra")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("senha")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean validateName(String name) {
        return name != null &&
                name.length() <= 50 &&
                name.matches("[A-Z ]+");
    }

    public static boolean validateUsername(String username) {
        return username != null &&
                username.length() == 7 &&
                username.matches("\\d+");
    }

    public static boolean validatePassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.length() <= 20 &&
                password.matches("[a-zA-Z]+");
    }
}
