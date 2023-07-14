package br.com.matheus.learningspring.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private HttpStatus statusCode;

    public AppException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
