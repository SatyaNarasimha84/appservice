package com.jayam.appservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super();
    }
    public QuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public QuestionNotFoundException(String message) {
        super(message);
    }
    public QuestionNotFoundException(Throwable cause) {
        super(cause);
    }
}