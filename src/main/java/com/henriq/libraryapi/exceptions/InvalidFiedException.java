package com.henriq.libraryapi.exceptions;

import lombok.Getter;

public class InvalidFiedException extends RuntimeException {

    @Getter
    private String field;

    public InvalidFiedException(String field, String message) {
        super(message);
        this.field = field;
    }
}
