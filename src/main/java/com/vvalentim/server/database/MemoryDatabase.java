package com.vvalentim.server.database;

import com.vvalentim.models.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryDatabase {
    private static MemoryDatabase instance = null;

    private final Map<String, User> users;

    private MemoryDatabase() {
        this.users = new HashMap<>();

        this.users.put("2099284", new User("JOAO VICTOR VALENTIM", "2099284", "administrador"));
    }

    public synchronized static MemoryDatabase getInstance() {
        if (instance == null) {
            instance = new MemoryDatabase();
        }

        return instance;
    }

    public synchronized User findUser(String username) {
        return users.get(username);
    }

    public synchronized void insertUser(User user) {
        this.users.put(user.getUsername(), user);
    }

    public synchronized void listAllUsers() {
        System.out.println("-- LIST USERS --");
        this.users.forEach((key, user) -> {
            System.out.println("user: " + user.getUsername());
        });
        System.out.println("-- END LIST USERS --");
    }
}
