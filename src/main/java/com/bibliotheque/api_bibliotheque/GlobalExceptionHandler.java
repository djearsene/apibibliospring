package com.bibliotheque.api_bibliotheque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LivreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLivreNotFound(LivreNotFoundException e) {
        Map<String, String> erreur = new HashMap<>();
        erreur.put("erreur", e.getMessage());
        erreur.put("status", "404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreur);
    }
}