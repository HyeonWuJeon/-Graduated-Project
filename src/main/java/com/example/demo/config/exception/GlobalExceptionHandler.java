package com.example.demo.config.exception;


import com.example.demo.config.exception.ExceptionResponse;
import com.example.demo.config.exception.MemberDuplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@RestControllerAdvice
public class GlobalExceptionHandler {


    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(MemberDuplicationException.class)
    public ResponseEntity<Object> DuplicationException(MemberDuplicationException ex, WebRequest request) {
        log.info("Exception Duplication [" + ex.getClass() + "] [" + ex.getMessage() + "]");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.FOUND); // 이메일 중복 302 code
    }
}