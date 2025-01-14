package com.vvalentim.server.commands.users;

import com.vvalentim.models.User;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.request.users.RequestUserCreate;
import com.vvalentim.protocol.response.users.ResponseUserCreated;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseUserAlreadyExists;
import com.vvalentim.server.commands.Command;
import com.vvalentim.server.database.MemoryDatabase;

final public class UserCreateCommand extends Command {
    private final RequestUserCreate payload;

    public UserCreateCommand(RequestPayload requestPayload) throws ClassCastException {
        this.payload = (RequestUserCreate) requestPayload;
    }

    @Override
    public void execute() {
        if (payload.isInvalid()) {
            this.result = new ResponseFailedValidation(RequestType.USER_CREATE.jsonKey);
            return;
        }

        MemoryDatabase db = MemoryDatabase.getInstance();
        User user = db.findUser(payload.username);

        if (user != null) {
            this.result = new ResponseUserAlreadyExists();
            return;
        }

        user = new User(payload.name, payload.username, payload.password);

        db.insertUser(user);

        this.result = new ResponseUserCreated("Cadastro realizado com sucesso.");
    }
}
