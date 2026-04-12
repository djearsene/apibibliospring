package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "API d'authentification")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // POST /auth/login
    @PostMapping("/login")
    @Operation(summary = "Se connecter et obtenir un token JWT")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(username);

        if (utilisateur.isPresent() &&
                passwordEncoder.matches(password, utilisateur.get().getPassword())) {
            String token = jwtUtil.genererToken(username, utilisateur.get().getRole());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", utilisateur.get().getRole());
            response.put("statut", utilisateur.get().getStatut());
            response.put("message", "Connexion réussie !");
            return ResponseEntity.ok(response);
        }

        Map<String, String> erreur = new HashMap<>();
        erreur.put("erreur", "Identifiants incorrects.");
        return ResponseEntity.status(401).body(erreur);
    }

    // POST /auth/register
    @PostMapping("/register")
    @Operation(summary = "Créer un nouveau compte utilisateur")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");
        String email = credentials.get("email");
        String ville = credentials.get("ville");
        String pays = credentials.get("pays");
        String statut = credentials.get("statut");

        if (utilisateurRepository.findByUsername(username).isPresent()) {
            Map<String, String> erreur = new HashMap<>();
            erreur.put("erreur", "Cet utilisateur existe déjà.");
            return ResponseEntity.status(400).body(erreur);
        }

        // Hacher le mot de passe avant de sauvegarder
        String motDePasseHache = passwordEncoder.encode(password);

        Utilisateur nouvelUtilisateur = new Utilisateur(
                username, motDePasseHache, email, ville, pays, statut, "ROLE_USER");
        utilisateurRepository.save(nouvelUtilisateur);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Compte créé avec succès !");
        response.put("role", "ROLE_USER");
        return ResponseEntity.ok(response);
    }
}