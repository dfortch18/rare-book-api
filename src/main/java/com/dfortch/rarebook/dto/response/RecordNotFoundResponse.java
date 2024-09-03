package com.dfortch.rarebook.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RecordNotFoundResponse extends MessageResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> fields;

    public RecordNotFoundResponse(String message, Map<String, Object> fields) {
        super(message, false);
        this.fields = fields;
    }
}
