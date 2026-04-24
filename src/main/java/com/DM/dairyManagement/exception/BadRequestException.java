package com.DM.dairyManagement.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
