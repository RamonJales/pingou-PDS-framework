package com.pds.pingou.produto.hq.handler;

import com.pds.pingou.produto.hq.exception.HqNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HqExceptionHandler {

    @ExceptionHandler(HqNotFoundException.class)
    public ResponseEntity<String> handleHqNotFoundException(HqNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}