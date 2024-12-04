package com.vvalentim.server;

import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.server.commands.Command;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Map;

public class RequestCommandFactory {
    private final Map<RequestType, Class<?>> commands = new EnumMap<>(RequestType.class);

    public void addCommand(RequestType type, Class<?> commandClass) {
        this.commands.put(type, commandClass);
    }

    public Command getCommandInstance(RequestType type, RequestPayload requestPayload) {
        Class<?> commandClass = this.commands.get(type);

        try {
            Constructor<?> constructor = commandClass.getConstructor(RequestPayload.class);

            return (Command) constructor.newInstance(requestPayload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get constructor for Command(" + type + ").");
        }
    }
}
