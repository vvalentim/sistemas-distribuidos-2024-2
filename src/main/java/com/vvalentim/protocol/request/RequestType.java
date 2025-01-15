package com.vvalentim.protocol.request;

import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import com.vvalentim.protocol.request.users.*;

public enum RequestType {
    LOGIN("login", RequestLogin.class),
    LOGOUT("logout", RequestLogout.class),

    USER_CREATE("cadastrarUsuario", RequestUserCreate.class),
    USER_LIST("listarUsuarios", RequestUserList.class),
    USER_FIND("localizarUsuario", RequestUserFind.class),
    USER_UPDATE("editarUsuario", RequestUserUpdate.class),
    USER_DELETE("excluirUsuario", RequestUserDelete.class);

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
