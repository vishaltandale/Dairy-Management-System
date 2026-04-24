package com.DM.dairyManagement.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
