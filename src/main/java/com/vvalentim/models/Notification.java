package com.vvalentim.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    private int id;
    private NotificationCategory category;
    private String title;
    private String description;

    public Notification(
        NotificationCategory category,
        String title,
        String description
    ) {
        this(0, category, title, description);
    }

    public Notification(
        @JsonProperty("id") int id,
        @JsonProperty("categoria") NotificationCategory category,
        @JsonProperty("titulo") String title,
        @JsonProperty("descricao") String description
    ) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("categoria")
    public NotificationCategory getCategory() {
        return category;
    }

    public void setCategory(NotificationCategory category) {
        this.category = category;
    }

    @JsonProperty("titulo")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("descricao")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static boolean validateId(int id) {
        return id > -1;
    }

    public static boolean validateExistingId(int id) {
        return id > 0;
    }

    public static boolean validateTitle(String title) {
        return
                title != null &&
                title.length() <= 100 &&
                title.matches("[A-Z ]+");
    }

    public static boolean validateDescription(String description) {
        return description != null && description.length() <= 500;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
