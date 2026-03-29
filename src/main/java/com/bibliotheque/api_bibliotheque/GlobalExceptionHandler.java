package com.bibliotheque.api_bibliotheque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Gestion du 404
    @ExceptionHandler(LivreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLivreNotFound(LivreNotFoundException e) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("erreur", e.getMessage());
        erreur.put("status", "404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreur);
    }

    // Gestion des erreurs de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> erreurs = new HashMap<>();
        for (FieldError erreur : e.getBindingResult().getFieldErrors()) {
            erreurs.put(erreur.getField(), erreur.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurs);
    }
}