package com.example.demo.config;


import com.example.demo.config.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler extends ApplicationService {

    //catch문 적용시안먹힌다.
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public void globalException(Exception e) {
        log.info("Exception [" + e.getClass() + "] [" + e.getMessage() + "]");

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String ValidationException(ConstraintViolationException e) {
        log.info("Exception Validation [" + e.getClass() + "] [" + e.getMessage() + "]");
        HashMap<String, Object> rtnMap = returnMap();

        rtnMap.put(AJAX_RESULT_TEXT, AJAX_RESULT_ILLEGAL_STATE); // 유효성 검증 오류
        return jsonFormatTransfer(rtnMap);


    }

}