package com.vvalentim.server.database;

import com.vvalentim.models.Notification;
import com.vvalentim.models.NotificationCategory;
import com.vvalentim.models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemoryDatabase {
    private final ConcurrentHashMap<String, User> users;
    private final ConcurrentHashMap<String, List<Integer>> subscriptions;
    private final CopyOnWriteArraySet<String> superUsers;
    private final List<String> onlineUsers;
    private final ObservableList<String> onlineUsersObservable;

    private final AtomicInteger notificationCategorySerialId;
    private final ConcurrentHashMap<Integer, NotificationCategory> notificationCategories;

    private final AtomicInteger notificationSerialId;
    private final ConcurrentHashMap<Integer, Notification> notifications;

    private MemoryDatabase() {
        this.users = new ConcurrentHashMap<>();
        this.subscriptions = new ConcurrentHashMap<>();
        this.superUsers = new CopyOnWriteArraySet<>();
        this.onlineUsers = new ArrayList<>();
        this.onlineUsersObservable = FXCollections.observableArrayList();

        this.insertSuperUser(new User("JOAO VICTOR VALENTIM", "2099284", "administrador"));
        this.insertUser(new User("FULANO DA SILVA", "1234567", "abcabcab"));
        this.insertUser(new User("CICLANO SOUZA", "0000000", "senhaciclano"));

        this.notificationCategorySerialId = new AtomicInteger(1);
        this.notificationCategories = new ConcurrentHashMap<>();

        this.saveNotificationCategory(new NotificationCategory("CLIMA"));
        this.saveNotificationCategory(new NotificationCategory("ESPORTES"));
        this.saveNotificationCategory(new NotificationCategory("CELEBRIDADES"));
        this.saveNotificationCategory(new NotificationCategory("POLITICA"));

        this.notificationSerialId = new AtomicInteger(1);
        this.notifications = new ConcurrentHashMap<>();

        this.saveNotification(new Notification(
            this.findNotificationCategory(1),
            "EXEMPLO TITULO DO AVISO",
            "Essa é a descrição de um aviso..."
        ));

        this.saveNotification(new Notification(
            this.findNotificationCategory(1),
            "OUTRO TITULO",
            "Essa é a OUTRA descrição de um aviso..."
        ));
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

    public ObservableList<String> getOnlineUsersObservable() {
        return this.onlineUsersObservable;
    }

    /* Start USER methods */
    public User findUser(String username) {
        return this.users.get(username);
    }

    public List<User> fetchAllUsers() {
        List<User> users = new ArrayList<>(this.users.values());

        return Collections.unmodifiableList(users);
    }

    public void insertUser(User user) {
        this.users.put(user.getUsername(), user);
        this.subscriptions.put(user.getUsername(), new ArrayList<>());
    }

    public void insertSuperUser(User user) {
        this.insertUser(user);
        this.superUsers.add(user.getUsername());
    }

    public void updateUser(User user) {
        this.users.replace(user.getUsername(), user);
    }

    public void deleteUser(String username) {
        this.logout(username);
        this.users.remove(username);
        this.subscriptions.remove(username);
        this.superUsers.removeIf(token -> token.equals(username));
    }

    public boolean isSuperUser(String username) {
        return this.superUsers.contains(username);
    }

    public boolean isOnline(String username) {
        return this.onlineUsersObservable.contains(username);
    }

    public synchronized boolean login(String username) {
        if (this.onlineUsers.contains(username)) {
            return false;
        }

        Platform.runLater(() -> this.onlineUsersObservable.add(username));

        return this.onlineUsers.add(username);
    }

    public synchronized void logout(String username) {
        Platform.runLater(() -> this.onlineUsersObservable.removeIf(username::equals));

        this.onlineUsers.removeIf(username::equals);
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

    /* Start NOTIFICATION methods */
    public Notification findNotification(int id) {
        return this.notifications.get(id);
    }

    public List<Notification> fetchAllNotifications() {
        List<Notification> notifications = new ArrayList<>(this.notifications.values());

        return Collections.unmodifiableList(notifications);
    }

    public List<Notification> fetchNotificationsWithCategory(int categoryId) {
        return this.notifications
                .values()
                .stream()
                .filter(notification -> notification.getCategory().getId() == categoryId)
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveNotification(Notification notification) {
        // Insert when the object has no id
        if (notification.getId() == 0) {
            int newId = this.notificationSerialId.getAndIncrement();

            notification.setId(newId);
            this.notifications.put(newId, notification);

            return;
        }

        // Update when the object already exists
        this.notifications.replace(notification.getId(), notification);
    }

    public void deleteNotification(int id) {
        this.notifications.remove(id);
    }
    /* End NOTIFICATION methods */

    /* Start SUBSCRIPTION methods */
    public void subscribeOnCategory(String username, int categoryId) {
        List<Integer> userSubscriptions = this.subscriptions.get(username);

        if (userSubscriptions != null && !userSubscriptions.contains(categoryId)) {
            userSubscriptions.add(categoryId);
            Collections.sort(userSubscriptions);
        }
    }

    public void unsubscribeFromCategory(String username, int categoryId) {
        List<Integer> userSubscriptions = this.subscriptions.get(username);

        if (userSubscriptions != null) {
            userSubscriptions.removeIf(id -> id == categoryId);
        }
    }

    public List<Integer> fetchUserSubscribedCategories(String username) {
        return this.subscriptions.get(username);
    }

    public List<Notification> fetchUserNotifications(String username) {
        List<Integer> userSubscriptions = this.subscriptions.get(username);
        List<Notification> notifications = new ArrayList<>();

        if (userSubscriptions != null) {
            userSubscriptions.forEach(categoryId -> {
                notifications.addAll(this.fetchNotificationsWithCategory(categoryId));
            });
        }

        return notifications;
    }
    /* End SUBSCRIPTION methods*/
}
