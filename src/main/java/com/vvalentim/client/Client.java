package com.vvalentim.client;

import com.vvalentim.helpers.AddressPromptHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static PrintWriter output;
    private static BufferedReader input;

    public static void main(String[] args) {
        try {
            AddressPromptHelper addrHelper = new AddressPromptHelper();

            socket = new Socket(addrHelper.getAddress(), addrHelper.getPort());
            // socket = new Socket("127.0.0.1", 5200);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Successfully connected to: " + socket.toString());
        } catch (Exception e) {
            System.out.println("Fatal exception: " + e.getMessage());
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

                System.out.println("CLIENT SENT -> " + userInput);

                String response = input.readLine();

                if (response == null) {
                    break;
                }

                System.out.println("CLIENT RECEIVED <- " + response);
            }

            stdInput.close();
            output.close();
            input.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Fatal exception: " + e.getMessage());
        }
    }
}