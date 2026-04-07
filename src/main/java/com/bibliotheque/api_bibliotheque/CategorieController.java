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
@RequestMapping("/categories")
@Tag(name = "Catégories", description = "API de gestion des catégories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    // GET /categories
    @GetMapping
    @Operation(summary = "Récupérer toutes les catégories")
    public ResponseEntity<List<Categorie>> getToutesLesCategories() {
        return ResponseEntity.ok(categorieRepository.findAll());
    }

    // GET /categories/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie par son id")
    public ResponseEntity<Categorie> getCategorieParId(@PathVariable int id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            return ResponseEntity.ok(categorie.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /categories
    @PostMapping
    @Operation(summary = "Ajouter une nouvelle catégorie")
    public ResponseEntity<Categorie> ajouterCategorie(@Valid @RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categorieRepository.save(categorie));
    }

    // PUT /categories/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une catégorie")
    public ResponseEntity<Categorie> modifierCategorie(
            @PathVariable int id, @Valid @RequestBody Categorie categorieModifiee) {
        Optional<Categorie> categorieExistante = categorieRepository.findById(id);
        if (categorieExistante.isPresent()) {
            Categorie categorie = categorieExistante.get();
            categorie.setNom(categorieModifiee.getNom());
            categorie.setDescription(categorieModifiee.getDescription());
            return ResponseEntity.ok(categorieRepository.save(categorie));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /categories/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie")
    public ResponseEntity<String> supprimerCategorie(@PathVariable int id) {
        if (!categorieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categorieRepository.deleteById(id);
        return ResponseEntity.ok("Catégorie supprimée.");
    }
}