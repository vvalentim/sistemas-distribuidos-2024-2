package com.vvalentim.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private boolean isOpen = false;

    private ConnectionHandler() {}

    private static class InstanceHolder {
        public static final ConnectionHandler INSTANCE = new ConnectionHandler();
    }

    public static ConnectionHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public synchronized void open(String hostname, int port) throws IOException {
        if (this.isOpen) {
            System.out.println("Connection handler is already open!");
            return;
        }

        this.socket = new Socket(hostname, port);
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.isOpen = true;

        System.out.println("Client connected on: " + this.socket);
    }

    public synchronized void close() throws RuntimeException {
        if (!this.isOpen) {
            System.out.println("Connection handler is already closed!");
            return;
        }

        try {
            this.output.close();
            this.output = null;

            this.input.close();
            this.input = null;

            this.socket.close();
            this.socket = null;

            this.isOpen = false;
        } catch (IOException e) {
            if (!(e instanceof SocketException)) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Server socket has been closed.");
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    /** TODO: verificar se a conexão com o socket/streams ainda está ativa antes de enviar ou receber mensagem **/
    public void sendMessage(String message) {
        if (message != null) {
            this.output.println(message);
        }
    }

    public String getResponse() throws IOException {
        String response = this.input.readLine();

        return response != null ? response : "";
    }
}
