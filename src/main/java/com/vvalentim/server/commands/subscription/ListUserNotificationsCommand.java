package com.vvalentim.server.commands.subscription;

import com.vvalentim.models.Notification;
import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.subscription.RequestUserNotifications;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.subscription.ResponseUserNotifications;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

import java.util.List;

public class ListUserNotificationsCommand extends Command {
    private final RequestUserNotifications payload;

    public ListUserNotificationsCommand(RequestPayload payload) {
        this.payload = (RequestUserNotifications) payload;
    }

    @Override
    public void execute() {
        if (this.payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.SUBSCRIPTION_USER_NOTIFICATIONS.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(this.payload.token);
        boolean isSuperUser = db.isSuperUser(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.SUBSCRIPTION_USER_NOTIFICATIONS.jsonKey);
            return;
        }

        User user = db.findUser(this.payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.SUBSCRIPTION_USER_NOTIFICATIONS, ErrorType.USER_NOT_FOUND);
            return;
        }

        List<Notification> notifications = db.fetchUserNotifications(this.payload.username);

        this.result = new ResponseUserNotifications(notifications);
    }
}
