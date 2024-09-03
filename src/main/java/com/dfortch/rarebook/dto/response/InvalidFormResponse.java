package com.dfortch.rarebook.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class InvalidFormResponse extends MessageResponse {

    private Map<String, List<String>> errors;

    public InvalidFormResponse(String message, Map<String, List<String>> errors) {
        super(message, false);
        this.errors = errors;
    }

    public InvalidFormResponse(String message) {
        super(message, false);
        this.errors = new HashMap<>();
    }
}
