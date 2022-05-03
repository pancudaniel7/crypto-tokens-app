package com.crypto.tokens.model.error;

public class FailToPersistException extends RuntimeException{

    public FailToPersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
