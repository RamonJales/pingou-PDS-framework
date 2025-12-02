package com.pds.pingou.camisa.handler;

import com.pds.pingou.camisa.exception.CamisaNotFoundException;
import com.pds.pingou.camisa.planos.exception.PlanoCamisaNotFoundException;
import com.pds.pingou.camisa.pacote.exception.PacoteCamisaNotFoundException;
import com.pds.pingou.camisa.assinatura.exception.AssinaturaCamisaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções para a aplicação de camisas.
 */
@RestControllerAdvice
public class CamisaExceptionHandler {
    
    @ExceptionHandler(CamisaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCamisaNotFoundException(CamisaNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PlanoCamisaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePlanoCamisaNotFoundException(PlanoCamisaNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PacoteCamisaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePacoteCamisaNotFoundException(PacoteCamisaNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AssinaturaCamisaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAssinaturaCamisaNotFoundException(AssinaturaCamisaNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse("Erro interno do servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
