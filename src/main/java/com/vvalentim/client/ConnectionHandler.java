package com.vvalentim.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler {
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader input;

    public ConnectionHandler() throws IOException {
        this("127.0.0.1", 5200);
    }

    public ConnectionHandler(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Client connected on: " + this.socket);
    }

    public void sendMessage(String message) {
        if (message != null) {
            this.output.println(message);
        }
    }

    public String getResponse() throws IOException {
        String response = this.input.readLine();

        return response != null ? response : "";
    }

    public void close() throws RuntimeException {
        try {
            this.output.close();
            this.input.close();
            this.socket.close();
        } catch (IOException e) {
            if (!(e instanceof SocketException)) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("Server socket has been closed.");
    }
}
