package com.henriq.libraryapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDate timestamp,
        Map<String, String> errors) {
            
    public ErrorResponse(int status, String error, String message) {
        this(status, error, message, LocalDate.now(), null);
    }

    public ErrorResponse(int status, String error, String message, Map<String, String> errors) {
        this(status, error, message, LocalDate.now(), errors);
    }
}