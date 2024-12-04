package com.vvalentim.server.commands.authentication;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.authentication.RequestUserSignup;
import com.vvalentim.protocol.response.authentication.ResponseSignup;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseUserAlreadyExists;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

final public class SignupCommand extends Command {
    private final RequestUserSignup payload;

    public SignupCommand(RequestPayload requestPayload) throws ClassCastException {
        this.payload = (RequestUserSignup) requestPayload;
    }

    @Override
    public void execute() {
        if (!payload.validate()) {
            result = new ResponseFailedValidation(RequestType.USER_SIGNUP.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        User user = db.findUser(payload.username);

        if (user != null) {
            result = new ResponseUserAlreadyExists();
            return;
        }

        user = new User(payload.name, payload.username, payload.password);

        db.insertUser(user);

        result = new ResponseSignup("Cadastro realizado com sucesso.");
    }
}
