package com.hkprogrammer.api.domain.view_models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatNotifyViewModel {

    @NotNull
    private Integer chat;

    @NotBlank
    private String message;

    @NotNull
    private NotificationUserType to;

    public enum NotificationUserType {
        USER("u"),
        SUPPLIER("s");

        private final String description;

        NotificationUserType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
