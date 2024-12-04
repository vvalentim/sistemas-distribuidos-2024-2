package com.vvalentim.models;

public class SignedUser {
    public final User user;
    public final String token;

    public SignedUser(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
