package com.jayam.appservice.exception;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ControllerAdvice
public class GlobalExceptionHandler {
 
    @ResponseBody
    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage questionNotFoundHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {        
        return  new ErrorMessage(HttpStatus.NOT_FOUND.value()+"","Record Not Found");
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorMessage handleMethodNotSupported(HttpServletRequest request) {
        return new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value()+"", "HTTP request method not supported for this operation.");
    }
     
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage handleIOException(HttpServletRequest request, Exception ex) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value()+"", "IO Error: " + ex.getMessage());
    } 
}