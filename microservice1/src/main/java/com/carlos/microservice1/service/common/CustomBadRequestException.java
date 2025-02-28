package com.carlos.microservice1.service.common;

import org.springframework.http.HttpStatus;

public class CustomBadRequestException extends RuntimeException {
    private HttpStatus status;

    public CustomBadRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}