package com.vvalentim.protocol.request;

import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import com.vvalentim.protocol.request.notification.RequestNotificationDelete;
import com.vvalentim.protocol.request.notification.RequestNotificationFind;
import com.vvalentim.protocol.request.notification.RequestNotificationList;
import com.vvalentim.protocol.request.notification.RequestNotificationSave;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryDelete;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryFind;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategoryList;
import com.vvalentim.protocol.request.notificationCategories.RequestNotificationCategorySave;
import com.vvalentim.protocol.request.users.*;

public enum RequestType {
    LOGIN("login", RequestLogin.class),
    LOGOUT("logout", RequestLogout.class),

    USER_CREATE("cadastrarUsuario", RequestUserCreate.class),
    USER_LIST("listarUsuarios", RequestUserList.class),
    USER_FIND("localizarUsuario", RequestUserFind.class),
    USER_UPDATE("editarUsuario", RequestUserUpdate.class),
    USER_DELETE("excluirUsuario", RequestUserDelete.class),

    NOTIFICATION_CATEGORY_SAVE("salvarCategoria", RequestNotificationCategorySave.class),
    NOTIFICATION_CATEGORY_LIST("listarCategorias", RequestNotificationCategoryList.class),
    NOTIFICATION_CATEGORY_FIND("localizarCategoria", RequestNotificationCategoryFind.class),
    NOTIFICATION_CATEGORY_DELETE("excluirCategoria", RequestNotificationCategoryDelete.class),

    NOTIFICATION_SAVE("salvarAviso", RequestNotificationSave.class),
    NOTIFICATION_LIST("listarAvisos", RequestNotificationList.class),
    NOTIFICATION_FIND("localizarAviso", RequestNotificationFind.class),
    NOTIFICATION_DELETE("excluirAviso", RequestNotificationDelete.class);

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
