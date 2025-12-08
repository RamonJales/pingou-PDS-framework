package com.pds.pingou.framework.core.security.handler;

import com.pds.pingou.framework.core.security.exception.UserDuplicatedException;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para exceções de autenticação e autorização.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<Map<String, Object>> handleUserDuplicatedException(UserDuplicatedException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return buildErrorResponse("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
