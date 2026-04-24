package com.DM.dairyManagement.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
