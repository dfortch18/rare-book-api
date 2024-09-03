package com.dfortch.rarebook.common.exception;

import lombok.Getter;

import java.util.Map;

/**
 * Exception thrown when a conflict occurs during the creation or update of a record.
 *
 * <p>
 * This exception is typically used to indicate that a specific entity or resource, identified
 * by certain field values, already exists or causes a conflict with the existing data.
 * It provides information about the type of the conflicting record and the specific fields that caused the conflict.
 * </p>
 */
@Getter
public class RecordConflictException extends RuntimeException {

    private final Class<?> recordClass;

    private final transient Map<String, String> conflictingFields;

    /**
     * Constructs a new RecordConflictException for a specific class type, with field errors provided.
     *
     * @param recordClass the class type of the record that caused the conflict
     * @param conflictingFields a map containing the fields that caused the conflict and their corresponding error messages
     */
    public RecordConflictException(Class<?> recordClass, Map<String, String> conflictingFields) {
        super("Record conflict found with " + recordClass.getSimpleName());
        this.recordClass = recordClass;
        this.conflictingFields = conflictingFields;
    }
}
