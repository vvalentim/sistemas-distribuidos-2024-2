package com.vvalentim.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveNotificationDto {
    public final int id;
    public final int categoryId;
    public final String title;
    public final String description;

    public SaveNotificationDto(
        @JsonProperty("id") int id,
        @JsonProperty("categoria") int categoryId,
        @JsonProperty("titulo") String title,
        @JsonProperty("descricao") String description
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaveNotificationDto{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
