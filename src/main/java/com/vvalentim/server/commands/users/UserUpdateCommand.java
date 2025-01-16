package com.vvalentim.server.commands.users;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.users.RequestUserUpdate;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.users.ResponseUserDeleted;
import com.vvalentim.protocol.response.users.ResponseUserUpdated;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class UserUpdateCommand extends Command {
    private final RequestUserUpdate payload;

    public UserUpdateCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestUserUpdate) payload;
    }

    @Override
    public void execute() {
        if (payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.USER_UPDATE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isOnline = db.isOnline(payload.token);
        boolean isSuperUser = db.isSuperUser(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.user.getUsername()))
        ) {
            this.result = new ResponseUnauthorized(RequestType.USER_UPDATE.jsonKey);
            return;
        }

        User user = db.findUser(payload.user.getUsername());

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.USER_UPDATE, ErrorType.USER_NOT_FOUND);
            return;
        }

        db.updateUser(payload.user);

        this.result = new ResponseUserUpdated("Edição realizada com sucesso.");
    }
}
