package com.vvalentim.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private final Socket connection;
    PrintWriter output;
    BufferedReader input;

    public ConnectionHandler(Socket connection) throws IOException {
        this.connection = connection;

        this.output = new PrintWriter(this.connection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
    }

    @Override
    public void run() {
        try {
            for (String line = this.input.readLine(); line != null; line = this.input.readLine()) {
                System.out.println("SERVER RECEIVED: " + line);

                this.output.println(line.toUpperCase());

                if (line.equals("bye")) {
                    break;
                }
            }

            this.output.close();
            this.input.close();
            this.connection.close();
        } catch (IOException e) {}

        System.out.println("Connection handler (" + this.connection.toString() + ") has stopped.");
    }
}
