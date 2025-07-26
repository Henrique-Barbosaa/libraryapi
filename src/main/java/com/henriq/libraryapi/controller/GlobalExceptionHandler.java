package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleArgsNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<com.henriq.libraryapi.dto.FieldError> errorsList = fieldErrors
                .stream()
                .map(f -> new com.henriq.libraryapi.dto.FieldError(f.getField(), f.getDefaultMessage()))
                .toList();

        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação: campos inválidos",
                errorsList);
    }
}
