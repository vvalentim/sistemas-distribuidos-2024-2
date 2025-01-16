package com.vvalentim.server;

import com.vvalentim.exceptions.InvalidPayloadFieldType;
import com.vvalentim.exceptions.RequestTypeNotSupported;
import com.vvalentim.exceptions.UnprocessableContentException;
import com.vvalentim.protocol.request.RequestPayload;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.protocol.response.ResponsePayload;
import com.vvalentim.protocol.response.errors.ResponseFailedValidation;
import com.vvalentim.protocol.response.errors.ResponseInvalidOperation;
import com.vvalentim.protocol.response.errors.ResponseUnprocessableContent;
import com.vvalentim.server.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private final RequestCommandFactory requestCommandFactory;
    private final Socket connection;
    private final PrintWriter output;
    private final BufferedReader input;
    private final String client;

    public ConnectionHandler(RequestCommandFactory requestCommandFactory, Socket connection) throws IOException {
        this.requestCommandFactory = requestCommandFactory;
        this.connection = connection;
        this.output = new PrintWriter(this.connection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        this.client = this.connection.getInetAddress() + ":" + this.connection.getPort();
    }

    private void printProtocolException(String exceptionMessage) {
        System.out.println("Protocol Exception [" + this.client + "]: " + exceptionMessage);
    }

    private String parseRequest(String requestMessage) {
        MessageParser parser = new MessageParser();
        ResponsePayload responseObject = null;
        String response;
        Exception exception = null;

        try {
            RequestPayload requestPayload = parser.deserialize(requestMessage);
            RequestType type = RequestType.getFromKey(requestPayload.requestType);
            Command command = this.requestCommandFactory.getCommandInstance(type, requestPayload);

            command.execute();

            responseObject = command.getResult();
        } catch (UnprocessableContentException e) {
            this.printProtocolException(e.getMessage());

            exception = e;

            responseObject = new ResponseUnprocessableContent();
        } catch (RequestTypeNotSupported e) {
            this.printProtocolException(e.getMessage());

            exception = e;

            responseObject = new ResponseInvalidOperation(e.requestType);
        } catch (InvalidPayloadFieldType e) {
            this.printProtocolException(e.getMessage());

            exception = e;
            
            responseObject = new ResponseFailedValidation(e.requestType);
        } finally {
            if (Server.DEBUG && exception != null) {
                exception.printStackTrace();
            }

            response = parser.serialize(responseObject);
        }

        return response;
    }

    @Override
    public void run() {
        try {
            for (String message = this.input.readLine(); message != null; message = this.input.readLine()) {
                System.out.println("SERVER RECEIVED FROM [" + this.client + "] <- " + message);

                String response = parseRequest(message);

                System.out.println("SERVER SENT TO [" + this.client + "] -> " + response);

                this.output.println(response);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Fatal error [" + this.client + "]: " + e.getMessage());
        } finally {
            try {
                this.output.close();
                this.input.close();
                this.connection.close();

                System.out.println("Connection handler (" + this.client + ") has stopped.");
            } catch (IOException e) {
                System.out.println("Failed to close connection handler (" + this.client + ").");
            }
        }
    }
}
