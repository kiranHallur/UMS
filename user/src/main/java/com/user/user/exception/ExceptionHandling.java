package com.user.user.exception;

import com.user.user.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice  //where ever the exception occur that comes here and that needs to be handled
public class ExceptionHandling {
    //to handle exception

    @ExceptionHandler(ResourceNotFoundException.class)
                    // @ExceptionHandler whenever the exception comes in ResourceNotFoundException class it will handle
    public ResponseEntity<ErrorDetails> resourceNotFoundException(
            ResourceNotFoundException ex, //ex as address
            WebRequest webRequest
    ){
        ErrorDetails error= new ErrorDetails(
                ex.getMessage(),
                new Date(),
                webRequest.getDescription(false)  //false gives only url not the client info i.e postman info
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> globalException(
            Exception ex //ex as address
    ){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
