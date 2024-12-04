package com.vvalentim.protocol.response;

public enum ResponseStatus {
    OK(200),
    CREATED(201),
    UNAUTHORIZED(401);

    public final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public static ResponseStatus getFromCode(int value) {
        for (ResponseStatus status : values()) {
            if (status.code == value) {
                return status;
            }
        }

        return null;
    }
}
