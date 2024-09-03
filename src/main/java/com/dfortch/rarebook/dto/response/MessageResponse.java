package com.dfortch.rarebook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class MessageResponse {

    protected boolean success;

    protected String message;

    protected LocalDateTime timestamp;

    public MessageResponse(String message) {
        this(message, true);
    }

    public MessageResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }
}
