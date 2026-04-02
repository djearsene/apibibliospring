package com.bibliotheque.api_bibliotheque;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  // Clé secrète pour signer le token
  private final Key cleSecrete = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Durée de validité : 24 heures
  private final long DUREE = 1000 * 60 * 60 * 24;

  // Générer un token pour un utilisateur
  public String genererToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + DUREE))
        .signWith(cleSecrete)
        .compact();
  }

  // Extraire le nom d'utilisateur du token
  public String extraireUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(cleSecrete)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // Vérifier si le token est valide
  public boolean estValide(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(cleSecrete)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}