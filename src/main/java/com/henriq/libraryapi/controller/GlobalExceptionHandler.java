package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.InvalidFieldError;
import com.henriq.libraryapi.dto.ResponseError;
import com.henriq.libraryapi.exceptions.DuplicateRegistrationException;
import com.henriq.libraryapi.exceptions.InvalidFiedException;
import com.henriq.libraryapi.exceptions.OperationNotAllowedException;
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
    public ResponseError handleArgsNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<InvalidFieldError> errorsList = fieldErrors
                .stream()
                .map(f -> new InvalidFieldError(f.getField(), f.getDefaultMessage()))
                .toList();

        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação: campos inválidos",
                errorsList);
    }

    @ExceptionHandler(DuplicateRegistrationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicateRegistrationException(DuplicateRegistrationException e){
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotAllowedException(OperationNotAllowedException e){
        return ResponseError.badRequest(e.getMessage());
    }

    @ExceptionHandler(InvalidFiedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFiedException e){
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                List.of(new InvalidFieldError(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleGenericException(RuntimeException e){
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Por favor, entre em contato com o suporte",
                List.of());
    }
}
