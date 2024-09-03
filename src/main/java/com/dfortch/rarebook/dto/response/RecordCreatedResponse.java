package com.dfortch.rarebook.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RecordCreatedResponse extends MessageResponse {

    private Long recordId;

    public RecordCreatedResponse(String message, Long recordId) {
        super(message, true);
        this.recordId = recordId;
    }
}
