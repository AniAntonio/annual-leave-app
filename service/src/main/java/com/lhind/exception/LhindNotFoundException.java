package com.lhind.exception;

import com.lhind.util.NoData;

public class LhindNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LhindNotFoundException(NoData notFound) {
        super(notFound.getMessage());
    }
}
