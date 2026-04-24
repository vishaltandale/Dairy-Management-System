package com.DM.dairyManagement.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
    
    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
