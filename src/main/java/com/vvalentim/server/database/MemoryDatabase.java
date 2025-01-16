package com.vvalentim.server.database;

import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryDatabase {
    private final ConcurrentHashMap<String, User> users;
    private final CopyOnWriteArraySet<String> superUsers;
    private final ConcurrentSkipListSet<String> onlineUsers;

    private AtomicInteger notificationCategorySerialId;
    private final ConcurrentHashMap<Integer, NotificationCategory> notificationCategories;

    private MemoryDatabase() {
        this.users = new ConcurrentHashMap<>();
        this.superUsers = new CopyOnWriteArraySet<>();
        this.onlineUsers = new ConcurrentSkipListSet<>();

        this.insertSuperUser(new User("JOAO VICTOR VALENTIM", "2099284", "administrador"));
        this.insertUser(new User("FULANO DA SILVA", "1234567", "abcabcab"));
        this.insertUser(new User("CICLANO SOUZA", "0000000", "senhaciclano"));

        this.notificationCategorySerialId = new AtomicInteger(1);
        this.notificationCategories = new ConcurrentHashMap<>();
    }

    private static class LazyHolder {
        public static final MemoryDatabase INSTANCE = new MemoryDatabase();
    }

    /**
     * <a href="http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html">Initialization On Demand Holder idiom</a>
     */
    public static MemoryDatabase getInstance() {
        return LazyHolder.INSTANCE;
    }

    /* Start USER methods */
    public User findUser(String username) {
        return this.users.get(username);
    }

    public List<User> fetchAllUsers() {
        List<User> users = new ArrayList<>(this.users.values());

        return Collections.unmodifiableList(users);
    }

    public List<String> fetchOnlineUsers() {
        List<String> users = new ArrayList<>(this.onlineUsers);

        return Collections.unmodifiableList(users);
    }

    public void insertUser(User user) {
        this.users.put(user.getUsername(), user);
    }

    public void insertSuperUser(User user) {
        this.insertUser(user);
        this.superUsers.add(user.getUsername());
    }

    public void updateUser(User user) {
        this.users.replace(user.getUsername(), user);
    }

    public void deleteUser(String username) {
        this.users.remove(username);
        this.superUsers.removeIf(key -> key.equals(username));
        this.onlineUsers.removeIf(key -> key.equals(username));
    }

    public boolean isSuperUser(String username) {
        return this.superUsers.contains(username);
    }

    public boolean isOnline(String username) {
        return this.onlineUsers.contains(username);
    }

    public boolean login(String username) {
        return this.onlineUsers.add(username);
    }

    public boolean logout(String username) {
        return this.onlineUsers.removeIf(key -> key.equals(username));
    }
    /* End USER methods */

    /* Start NOTIFICATION CATEGORY methods */
    public NotificationCategory findNotificationCategory(int id) {
        return this.notificationCategories.get(id);
    }

    public List<NotificationCategory> fetchAllNotificationCategories() {
        List<NotificationCategory> categories = new ArrayList<>(this.notificationCategories.values());

        return Collections.unmodifiableList(categories);
    }

    public void saveNotificationCategory(NotificationCategory category) {
        // Insert when the object has no id
        if (category.getId() == 0) {
            int newId = this.notificationCategorySerialId.getAndIncrement();

            category.setId(newId);
            this.notificationCategories.put(newId, category);

            return;
        }

        // Update when the object already exists
        this.notificationCategories.replace(category.getId(), category);
    }

    public void deleteNotificationCategory(int id) {
        this.notificationCategories.remove(id);
    }
    /* End NOTIFICATION CATEGORY methods */
}
