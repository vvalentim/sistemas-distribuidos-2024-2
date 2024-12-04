package com.vvalentim.server.commands;

import com.vvalentim.protocol.response.ResponsePayload;

abstract public class Command {
    protected ResponsePayload result;
    abstract public void execute();

    public ResponsePayload getResult() {
        return this.result;
    }
}
