package com.personal.userservice.exceptions;

public class InvalidOrExpiredTokenException extends Exception{
    public InvalidOrExpiredTokenException(String message) {
        super(message);
    }
}
