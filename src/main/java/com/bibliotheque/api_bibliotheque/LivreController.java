package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livres")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;

    // GET /livres : retourne tous les livres
    @GetMapping
    public ResponseEntity<List<Livre>> getTousLesLivres() {
        List<Livre> livres = livreRepository.findAll();
        return ResponseEntity.ok(livres);
    }

    // GET /livres/{id} : retourne un livre par son id
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreParId(@PathVariable int id) {
        Optional<Livre> livre = livreRepository.findById(id);
        if (livre.isPresent()) {
            return ResponseEntity.ok(livre.get());
        }
        throw new LivreNotFoundException(id);
    }

    // POST /livres : ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<Livre> ajouterLivre(@RequestBody Livre livre) {
        Livre nouveauLivre = livreRepository.save(livre);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauLivre);
    }

    // PUT /livres/{id} : modifier un livre existant
    @PutMapping("/{id}")
    public ResponseEntity<Livre> modifierLivre(@PathVariable int id, @RequestBody Livre livreModifie) {
        Optional<Livre> livreExistant = livreRepository.findById(id);
        if (livreExistant.isPresent()) {
            Livre livre = livreExistant.get();
            livre.setTitre(livreModifie.getTitre());
            livre.setAuteur(livreModifie.getAuteur());
            livre.setAnnee(livreModifie.getAnnee());
            return ResponseEntity.ok(livreRepository.save(livre));
        }
        throw new LivreNotFoundException(id);
    }

    // DELETE /livres/{id} : supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerLivre(@PathVariable int id) {
        if (!livreRepository.existsById(id)) {
            throw new LivreNotFoundException(id);
        }
        livreRepository.deleteById(id);
        return ResponseEntity.ok("Livre supprimé.");
    }
}