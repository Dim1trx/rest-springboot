package com.rodrigues.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundExceptioException extends RuntimeException {
    public MyFileNotFoundExceptioException(String message) {
        super(message);
    }

    public MyFileNotFoundExceptioException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
