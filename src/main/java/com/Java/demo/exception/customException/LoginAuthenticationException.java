package com.Java.demo.exception.customException;

public class LoginAuthenticationException extends RuntimeException {
    public LoginAuthenticationException(String message) {
        super(message);
    }
}
