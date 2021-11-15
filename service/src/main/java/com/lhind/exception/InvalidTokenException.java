package com.lhind.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public InvalidTokenException(String msg) {
        super(msg);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
