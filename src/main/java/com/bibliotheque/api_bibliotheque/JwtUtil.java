package com.bibliotheque.api_bibliotheque;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key cleSecrete = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long DUREE = 1000 * 60 * 60 * 24;

    // Générer un token avec le rôle
    public String genererToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DUREE))
                .signWith(cleSecrete)
                .compact();
    }

    // Extraire le username
    public String extraireUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extraire le rôle
    public String extraireRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Vérifier si le token est valide
    public boolean estValide(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Méthode privée pour extraire les claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(cleSecrete)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}