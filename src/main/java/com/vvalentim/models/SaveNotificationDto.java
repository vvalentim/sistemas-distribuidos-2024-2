package com.vvalentim.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveNotificationDto {
    @JsonProperty("id")
    public final int id;

    @JsonProperty("categoria")
    public final int categoryId;

    @JsonProperty("titulo")
    public final String title;

    @JsonProperty("descricao")
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
