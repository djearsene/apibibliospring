package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "API d'authentification")
public class AuthController {

  @Autowired
  private JwtUtil jwtUtil;

  // POST /auth/login
  @PostMapping("/login")
  @Operation(summary = "Se connecter et obtenir un token JWT")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {

    String username = credentials.get("username");
    String password = credentials.get("password");

    // Vérification simple pour l'exemple
    if ("admin".equals(username) && "admin123".equals(password)) {
      String token = jwtUtil.genererToken(username);
      Map<String, String> response = new HashMap<>();
      response.put("token", token);
      response.put("message", "Connexion réussie !");
      return ResponseEntity.ok(response);
    }

    Map<String, String> erreur = new HashMap<>();
    erreur.put("erreur", "Identifiants incorrects.");
    return ResponseEntity.status(401).body(erreur);
  }
}