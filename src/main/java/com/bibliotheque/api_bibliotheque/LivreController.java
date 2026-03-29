package com.bibliotheque.api_bibliotheque;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    // GET /livres
    @GetMapping
    public ResponseEntity<List<Livre>> getTousLesLivres() {
        return ResponseEntity.ok(livreService.getTousLesLivres());
    }

    // GET /livres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreParId(@PathVariable int id) {
        return ResponseEntity.ok(livreService.getLivreParId(id));
    }

    // POST /livres
    @PostMapping
    public ResponseEntity<Livre> ajouterLivre(@Valid @RequestBody Livre livre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livreService.ajouterLivre(livre));
    }

    // PUT /livres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Livre> modifierLivre(@PathVariable int id, @Valid @RequestBody Livre livreModifie) {
        return ResponseEntity.ok(livreService.modifierLivre(id, livreModifie));
    }

    // DELETE /livres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerLivre(@PathVariable int id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.ok("Livre supprimé.");
    }
}