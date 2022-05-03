package com.crypto.tokens.model.error;

public class FailToPersistInMemoryConflictException extends RuntimeException{
    public FailToPersistInMemoryConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
