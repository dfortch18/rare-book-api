package com.dfortch.rarebook.common.exception;

import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;

/**
 * Exception thrown when a requested record cannot be found in the database.
 *
 * <p>
 * This exception is typically used to indicate that a specific entity or resource, identified
 * by certain search criteria, does not exist in the data store. It provides information about
 * the type of the missing record and the search criteria that were used to look it up.
 * </p>
 */
@Getter
public class RecordNotFoundException extends RuntimeException {

    private final Class<?> recordClass;

    private final transient Map<String, Object> criteria;

    /**
     * Constructs a new RecordNotFoundException for a specific class type, with no search fields provided.
     *
     * @param recordClass the class type of the record that was not found
     */
    public RecordNotFoundException(Class<?> recordClass) {
        super("Record not found of " + recordClass.getSimpleName());
        this.recordClass = recordClass;
        this.criteria = new TreeMap<>();
    }

    /**
     * Constructs a new RecordNotFoundException for a specific class type, with search fields provided.
     *
     * @param recordClass the class type of the record that was not found
     * @param criteria a map containing the search criteria that was used to look up the record
     */
    public RecordNotFoundException(Class<?> recordClass, Map<String, Object> criteria) {
        super("Resource not found of " + recordClass.getSimpleName());
        this.recordClass = recordClass;
        this.criteria = criteria;
    }
}
