package com.vvalentim.helpers;

import com.google.common.net.InetAddresses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddressPromptHelper {
    private final BufferedReader input;

    public AddressPromptHelper() {
        this.input = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getAddress() throws IOException {
        String ip = null;

        while (ip == null || !InetAddresses.isInetAddress(ip)) {
            System.out.print("Host: ");
            ip = this.input.readLine();

            if (!InetAddresses.isInetAddress(ip)) {
                System.out.println("Invalid host address.");
                continue;
            }
        }

        return ip;
    }

    public int getPort() throws IOException {
        int port = 0;

        while (port == 0) {
            System.out.print("Port: ");
            String userInput = input.readLine();

            if (
                userInput == null ||
                !userInput.matches("^[0-9]*$") ||
                Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > 65535
            ) {
                System.out.println("Invalid port.");
                continue;
            }

            port = Integer.parseInt(userInput);
        }

        return port;
    }
}
