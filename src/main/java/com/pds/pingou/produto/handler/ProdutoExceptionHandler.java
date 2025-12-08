package com.pds.pingou.produto.handler;

import com.pds.pingou.produto.exception.ProdutoDuplicatedException;
import com.pds.pingou.produto.exception.ProdutoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções para produtos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@RestControllerAdvice
public class ProdutoExceptionHandler {
    
    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProdutoNotFound(ProdutoNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    
    @ExceptionHandler(ProdutoDuplicatedException.class)
    public ResponseEntity<Map<String, Object>> handleProdutoDuplicated(ProdutoDuplicatedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
