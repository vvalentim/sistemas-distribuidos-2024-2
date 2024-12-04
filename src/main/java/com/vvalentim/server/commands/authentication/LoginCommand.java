package com.vvalentim.server.commands.authentication;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.authentication.RequestLogin;
import com.vvalentim.protocol.response.authentication.ResponseLogin;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseIncorrectCredentials;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

final public class LoginCommand extends Command {
    private final RequestLogin payload;

    public LoginCommand(RequestPayload requestPayload) throws ClassCastException {
        this.payload = (RequestLogin) requestPayload;
    }

    @Override
    public void execute() {
        if (!payload.validate()) {
            result = new ResponseFailedValidation(RequestType.LOGIN.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        User user = db.findUser(payload.username);

        if (user == null || !user.getPassword().equals(payload.password)) {
            result = new ResponseIncorrectCredentials();
            return;
        }

        db.listAllUsers();

        result = new ResponseLogin(user.getUsername());
    }
}
