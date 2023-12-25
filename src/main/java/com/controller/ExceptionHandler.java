package com.controller;

import com.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidDataException.class, InvalidEmailException.class, InvalidLoginException.class
    , InvalidSaltException.class,InvalidUsernameException.class})
    public ResponseEntity<ErrorDetails> handleBadRequest(RuntimeException e){
        ErrorDetails errorDetails=new ErrorDetails(e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
