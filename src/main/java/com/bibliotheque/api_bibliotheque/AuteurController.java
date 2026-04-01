package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auteurs")
@Tag(name = "Auteurs", description = "API de gestion des auteurs")
public class AuteurController {

    @Autowired
    private AuteurRepository auteurRepository;

    // GET /auteurs
    @GetMapping
    @Operation(summary = "Récupérer tous les auteurs")
    public ResponseEntity<List<Auteur>> getTousLesAuteurs() {
        return ResponseEntity.ok(auteurRepository.findAll());
    }

    // GET /auteurs/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un auteur par son id")
    public ResponseEntity<Auteur> getAuteurParId(@PathVariable int id) {
        Optional<Auteur> auteur = auteurRepository.findById(id);
        if (auteur.isPresent()) {
            return ResponseEntity.ok(auteur.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /auteurs
    @PostMapping
    @Operation(summary = "Ajouter un nouvel auteur")
    public ResponseEntity<Auteur> ajouterAuteur(@Valid @RequestBody Auteur auteur) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auteurRepository.save(auteur));
    }

    // DELETE /auteurs/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un auteur")
    public ResponseEntity<String> supprimerAuteur(@PathVariable int id) {
        auteurRepository.deleteById(id);
        return ResponseEntity.ok("Auteur supprimé.");
    }
}