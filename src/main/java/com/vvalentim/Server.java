package com.vvalentim;

import com.vvalentim.helpers.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final String hostname;

    private final int port;

    private ServerSocket socket = null;

    private boolean isRunning = false;

    public Server() {
        this("127.0.0.1", 5200);
    }

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            System.out.println("Initializing server socket...");

            this.socket = new ServerSocket();
            this.socket.bind(new InetSocketAddress(this.hostname, this.port));
            this.isRunning = true;

            System.out.println(this.socket.toString());
            System.out.println("Server listening on " + this.hostname + ":"  + this.port + "...");

            while (this.isRunning) {
                System.out.println("Waiting for new connection...");

                Socket connection = this.socket.accept();

                System.out.println("Connection accepted from: " + connection.toString());

                ConnectionHandler handler = new ConnectionHandler(connection);

                handler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() throws IOException {
        this.isRunning = false;
        this.socket.close();
    }
}