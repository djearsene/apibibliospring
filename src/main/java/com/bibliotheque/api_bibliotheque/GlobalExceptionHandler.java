package com.bibliotheque.api_bibliotheque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LivreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLivreNotFound(LivreNotFoundException e) {
        logger.error("Erreur 404 : {}", e.getMessage());
        Map<String, String> erreur = new HashMap<>();
        erreur.put("erreur", e.getMessage());
        erreur.put("status", "404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreur);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        logger.warn("Erreur de validation : {}", e.getMessage());
        Map<String, String> erreurs = new HashMap<>();
        for (FieldError erreur : e.getBindingResult().getFieldErrors()) {
            erreurs.put(erreur.getField(), erreur.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurs);
    }
}