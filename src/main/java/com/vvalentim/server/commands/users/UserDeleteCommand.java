package com.vvalentim.server.commands.users;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.users.RequestUserDelete;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.users.ResponseUserDeleted;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class UserDeleteCommand extends Command {
    private final RequestUserDelete payload;

    public UserDeleteCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestUserDelete) payload;
    }

    @Override
    public void execute() {
        if (payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.USER_DELETE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isSuperUser = db.isSuperUser(payload.token);
        boolean isOnline = db.isOnline(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.USER_DELETE.jsonKey);
            return;
        }

        User user = db.findUser(payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.USER_DELETE, ErrorType.USER_NOT_FOUND);
            return;
        }

        db.deleteUser(payload.username);

        this.result = new ResponseUserDeleted("Exclusão realizada com sucesso.");
    }
}
