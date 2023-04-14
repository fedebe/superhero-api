package com.api.superhero.exception;

import org.springframework.http.HttpStatus;

public class SuperheroException extends RuntimeException {

    private static final long serialVersionUID = 6766941847060402810L;
    
    private final String message;
    
    private final HttpStatus status;
    
    public SuperheroException(HttpStatus status) {
        this.message = "There was an error during the excecution.";
        this.status = status;
    }
    
    public SuperheroException(Exception e, HttpStatus status) {
        super(e);
        this.message = "There was an error during the excecution.";
        this.status = status;
    }
    
    public SuperheroException(Exception e, String message, HttpStatus status) {
        super(e);
        this.message = message;
        this.status = status;
    }
    
    public SuperheroException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    
 
}
