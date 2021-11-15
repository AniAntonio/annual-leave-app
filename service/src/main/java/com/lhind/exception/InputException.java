package com.lhind.exception;

import com.lhind.util.BadRequest;

public class InputException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InputException(BadRequest badRequest) {
        super(badRequest.getMessage());
    }
}
