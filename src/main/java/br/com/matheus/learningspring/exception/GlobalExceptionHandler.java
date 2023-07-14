package br.com.matheus.learningspring.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<Object> handleAppException(final AppException ex) {
        final HashMap<String, String> returnObject = new HashMap<>();

        returnObject.put("message", ex.getMessage());

        return new ResponseEntity<>(returnObject, ex.getStatusCode());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex) {
        final HashMap<String, String> returnObject = new HashMap<>();

        returnObject.put("message", "Internal Server Error");

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(returnObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
