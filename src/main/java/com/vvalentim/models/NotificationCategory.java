package com.vvalentim.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationCategory {
    private int id;
    private String name;

    public NotificationCategory(String name) {
        this(0, name);
    }

    public NotificationCategory(
        @JsonProperty("id") int id,
        @JsonProperty("nome") String name
    ) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("nome")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean validateId(int id) {
        return id > -1;
    }

    public static boolean validateExistingId(int id) {
        return id > 0;
    }

    public static boolean validateName(String name) {
        return name != null &&
                name.length() <= 50 &&
                name.matches("[A-Z ]+");
    }

    @Override
    public String toString() {
        return "NotificationCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
