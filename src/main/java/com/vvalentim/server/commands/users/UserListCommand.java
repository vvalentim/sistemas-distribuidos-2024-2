package com.vvalentim.server.commands.users;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.users.RequestUserList;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseUnauthorized;
import com.vvalentim.protocol.response.users.ResponseUserList;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

final public class UserListCommand extends Command {
    private final RequestUserList payload;

    public UserListCommand(RequestPayload payload) throws ClassCastException {
        this.payload = (RequestUserList) payload;
    }

    @Override
    public void execute() {
        /* Check if the token has a valid "username" format */
        if (payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.USER_LIST.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        User user = db.findUser(payload.token);

        /* Check if user exists, is a superuser and is online */
        if (user == null || !db.isSuperUser(payload.token) || !db.isOnline(payload.token)) {
            this.result = new ResponseUnauthorized(RequestType.USER_LIST.jsonKey);
            return;
        }

        this.result = new ResponseUserList(db.fetchAllUsers());
    }
}
