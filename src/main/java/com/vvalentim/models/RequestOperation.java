package com.vvalentim.models;

public enum RequestOperation {
    LOGIN("login"),
    LOGOUT("logout"),
    USER_SIGNUP("cadastrarUsuario");

    private final String key;

    private RequestOperation(String key) {
        this.key = key;
    }

    public static RequestOperation getFromKey(String key) {
        for (RequestOperation operation : values()) {
            if (operation.key.equals(key)) {
                return operation;
            }
        }

        return null;
    }
}
