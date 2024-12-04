package com.vvalentim.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String hostname = "127.0.0.1";
    private static final int port = 5200;

    private static Socket socket;
    private static PrintWriter output;
    private static BufferedReader input;


    public static void main(String[] args) {
        try {
            socket = new Socket(hostname, port);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Successfully connected to: " + socket.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        BufferedReader stdInput = new BufferedReader(new InputStreamReader((System.in)));

        System.out.println("Send message: ");

        try {
            for (String userInput = stdInput.readLine(); userInput != null; userInput = stdInput.readLine()) {
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }

                output.println(userInput);

                String response = input.readLine();

                if (response == null) {
                    break;
                }

                System.out.println(response);
            }

            stdInput.close();
            output.close();
            input.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}