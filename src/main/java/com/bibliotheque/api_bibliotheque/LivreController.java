package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/livres")
@Tag(name = "Livres", description = "API de gestion des livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    // GET /livres avec pagination et tri
    @GetMapping
    @Operation(summary = "Récupérer tous les livres avec pagination et tri")
    public ResponseEntity<Page<Livre>> getTousLesLivres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(livreService.getTousLesLivres(pageable));
    }

    // GET /livres/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par son id")
    public ResponseEntity<Livre> getLivreParId(@PathVariable int id) {
        return ResponseEntity.ok(livreService.getLivreParId(id));
    }

    // GET /livres/recherche/titre
    @GetMapping("/recherche/titre")
    @Operation(summary = "Rechercher des livres par titre")
    public ResponseEntity<Page<Livre>> rechercherParTitre(
            @RequestParam String titre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(livreService.rechercherParTitre(titre, pageable));
    }

    // GET /livres/recherche/auteur
    @GetMapping("/recherche/auteur")
    @Operation(summary = "Rechercher des livres par auteur")
    public ResponseEntity<Page<Livre>> rechercherParAuteur(
            @RequestParam String auteur,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(livreService.rechercherParAuteur(auteur, pageable));
    }

    // POST /livres
    @PostMapping
    @Operation(summary = "Ajouter un nouveau livre")
    public ResponseEntity<Livre> ajouterLivre(@Valid @RequestBody Livre livre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livreService.ajouterLivre(livre));
    }

    // PUT /livres/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un livre existant")
    public ResponseEntity<Livre> modifierLivre(@PathVariable int id, @Valid @RequestBody Livre livreModifie) {
        return ResponseEntity.ok(livreService.modifierLivre(id, livreModifie));
    }

    // DELETE /livres/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre")
    public ResponseEntity<String> supprimerLivre(@PathVariable int id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.ok("Livre supprimé.");
    }

    // GET /livres/export/csv
@GetMapping("/export/csv")
@Operation(summary = "Exporter tous les livres en CSV")
public ResponseEntity<byte[]> exporterCSV() {
    String contenuCSV = livreService.exporterCSV();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("text/csv"));
    headers.setContentDispositionFormData("attachment", "livres.csv");

    return ResponseEntity.ok()
            .headers(headers)
            .body(contenuCSV.getBytes());
}
}