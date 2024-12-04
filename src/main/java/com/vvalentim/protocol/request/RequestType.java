package com.vvalentim.protocol.request;

import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import com.vvalentim.protocol.request.authentication.RequestUserSignup;

public enum RequestType {
    LOGIN("login", RequestLogin.class),
    LOGOUT("logout", RequestLogout.class),
    USER_SIGNUP("cadastrarUsuario", RequestUserSignup.class);

    public final String jsonKey;
    public final Class<?> payloadType;

    RequestType(String jsonKey, Class<?> payloadType) {
        this.jsonKey = jsonKey;
        this.payloadType = payloadType;
    }

    public static RequestType getFromKey(String key) {
        for (RequestType operation : values()) {
            if (operation.jsonKey.equals(key)) {
                return operation;
            }
        }

        return null;
    }
}
