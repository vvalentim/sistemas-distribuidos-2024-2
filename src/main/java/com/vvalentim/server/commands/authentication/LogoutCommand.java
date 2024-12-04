package com.vvalentim.server.commands.authentication;

import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.authentication.RequestLogout;
import com.vvalentim.protocol.response.authentication.ResponseLogout;
import com.vvalentim.server.commands.Command;

final public class LogoutCommand extends Command {
    private final RequestLogout payload;

    public LogoutCommand(RequestPayload requestPayload) throws ClassCastException {
        this.payload = (RequestLogout) requestPayload;
    }

    @Override
    public void execute() {
        // Não há nenhuma validação ou verificação do token no protocolo
        result = new ResponseLogout();
    }
}
