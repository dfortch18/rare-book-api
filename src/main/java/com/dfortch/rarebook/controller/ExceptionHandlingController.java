package com.dfortch.rarebook.controller;

import com.dfortch.rarebook.common.exception.RecordConflictException;
import com.dfortch.rarebook.common.exception.RecordNotFoundException;
import com.dfortch.rarebook.dto.response.InvalidFormResponse;
import com.dfortch.rarebook.dto.response.MessageResponse;
import com.dfortch.rarebook.dto.response.RecordNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({NoResourceFoundException.class, RecordNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNotFound(Exception ex) {
        if (ex instanceof RecordNotFoundException resourceNotFoundException) {
            return new RecordNotFoundResponse(ex.getMessage(), resourceNotFoundException.getCriteria());
        } else {
            return getErrorMessageResponse(ex);
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, RecordConflictException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidFormResponse handleInvalidFormExceptions(Exception ex) {
        Map<String, List<String>> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>())
                        .add(fieldError.getDefaultMessage());
            });
        } else if (ex instanceof RecordConflictException recordConflictException) {
            recordConflictException.getConflictingFields().forEach((key, value) -> errors.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(value));
        }

        return new InvalidFormResponse("Invalid Request Form", errors);
    }

    private MessageResponse getErrorMessageResponse(Exception ex) {
        MessageResponse response = new MessageResponse();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        response.setTimestamp(LocalDateTime.now());

        return response;
    }
}
