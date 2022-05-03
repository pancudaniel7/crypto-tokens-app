package com.crypto.tokens.controller;

import com.crypto.tokens.model.error.Errors;
import com.crypto.tokens.model.error.FailToPersistInMemoryConflictException;
import com.crypto.tokens.model.error.FailToPersistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FailToPersistException.class)
    public ResponseEntity<Errors> failToPersist(FailToPersistException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(new Errors(e.getMessage()));
    }

    @ExceptionHandler(FailToPersistInMemoryConflictException.class)
    public ResponseEntity<Errors> failToPersistInMemory(FailToPersistInMemoryConflictException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(409).body(new Errors(e.getMessage()));
    }
}
