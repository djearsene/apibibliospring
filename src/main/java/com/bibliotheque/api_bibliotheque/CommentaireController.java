package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commentaires")
@Tag(name = "Commentaires", description = "API de gestion des commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private LivreRepository livreRepository;

    // GET /commentaires/livre/{livreId}
    @GetMapping("/livre/{livreId}")
    @Operation(summary = "Voir les commentaires d'un livre")
    public ResponseEntity<List<Commentaire>> getCommentairesParLivre(
            @PathVariable int livreId) {
        return ResponseEntity.ok(
                commentaireRepository.findByLivreIdOrderByDateCommentaireDesc(livreId));
    }

    // GET /commentaires/utilisateur/{username}
    @GetMapping("/utilisateur/{username}")
    @Operation(summary = "Voir les commentaires d'un utilisateur")
    public ResponseEntity<List<Commentaire>> getCommentairesParUtilisateur(
            @PathVariable String username) {
        return ResponseEntity.ok(
                commentaireRepository.findByUsernameOrderByDateCommentaireDesc(username));
    }

    // POST /commentaires/livre/{livreId}
    @PostMapping("/livre/{livreId}")
    @Operation(summary = "Ajouter un commentaire sur un livre")
    public ResponseEntity<Commentaire> ajouterCommentaire(
            @PathVariable int livreId,
            @Valid @RequestBody Commentaire commentaire) {

        Optional<Livre> livre = livreRepository.findById(livreId);
        if (!livre.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        commentaire.setLivre(livre.get());
        commentaire.setUsername(username);
        commentaire.setDateCommentaire(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentaireRepository.save(commentaire));
    }

    // DELETE /commentaires/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un commentaire")
    public ResponseEntity<String> supprimerCommentaire(@PathVariable int id) {
        if (!commentaireRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentaireRepository.deleteById(id);
        return ResponseEntity.ok("Commentaire supprimé.");
    }
}