package com.vvalentim.server.commands.users;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.users.RequestUserFind;
import com.vvalentim.protocol.response.errors.ErrorType;
import com.vvalentim.protocol.response.errors.ResponseResourceNotFound;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.users.ResponseUserFound;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

public class UserFindCommand extends Command {
    private final RequestUserFind payload;

    public UserFindCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestUserFind) payload;
    }

    @Override
    public void execute() {
        if (payload.isInvalid()) {
            this.result = new ResponseUnauthorized(RequestType.USER_FIND.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        boolean isSuperUser = db.isSuperUser(payload.token);
        boolean isOnline = db.isOnline(payload.token);

        if (
            !isOnline ||
            (!isSuperUser && !payload.token.equals(payload.username))
        ) {
            this.result = new ResponseUnauthorized(RequestType.USER_FIND.jsonKey);
            return;
        }

        User user = db.findUser(payload.username);

        if (user == null) {
            this.result = new ResponseResourceNotFound(RequestType.USER_FIND, ErrorType.USER_NOT_FOUND);
            return;
        }

        this.result = new ResponseUserFound(user);
    }
}
