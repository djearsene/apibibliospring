
package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livres")
@Tag(name = "Livres", description = "API de gestion des livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping
    @Operation(summary = "Récupérer tous les livres avec pagination")
    public ResponseEntity<Page<Livre>> getTousLesLivres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(livreService.getTousLesLivres(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par son id")
    public ResponseEntity<Livre> getLivreParId(@PathVariable int id) {
        return ResponseEntity.ok(livreService.getLivreParId(id));
    }

    @PostMapping
    @Operation(summary = "Ajouter un nouveau livre")
    public ResponseEntity<Livre> ajouterLivre(@Valid @RequestBody Livre livre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livreService.ajouterLivre(livre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un livre existant")
    public ResponseEntity<Livre> modifierLivre(@PathVariable int id, @Valid @RequestBody Livre livreModifie) {
        return ResponseEntity.ok(livreService.modifierLivre(id, livreModifie));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre")
    public ResponseEntity<String> supprimerLivre(@PathVariable int id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.ok("Livre supprimé.");
    }
}