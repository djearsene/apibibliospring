package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Livre> getTousLesLivres() {
        return livreRepository.findAll();
    }

    // GET /livres/{id} : retourne un livre par son id
    @GetMapping("/{id}")
    public Livre getLivreParId(@PathVariable int id) {
        Optional<Livre> livre = livreRepository.findById(id);
        return livre.orElse(null);
    }

    // POST /livres : ajouter un nouveau livre
    @PostMapping
    public Livre ajouterLivre(@RequestBody Livre livre) {
        return livreRepository.save(livre);
    }

    // PUT /livres/{id} : modifier un livre existant
    @PutMapping("/{id}")
    public Livre modifierLivre(@PathVariable int id, @RequestBody Livre livreModifie) {
        Optional<Livre> livreExistant = livreRepository.findById(id);
        if (livreExistant.isPresent()) {
            Livre livre = livreExistant.get();
            livre.setTitre(livreModifie.getTitre());
            livre.setAuteur(livreModifie.getAuteur());
            livre.setAnnee(livreModifie.getAnnee());
            return livreRepository.save(livre);
        }
        return null;
    }

    // DELETE /livres/{id} : supprimer un livre
    @DeleteMapping("/{id}")
    public String supprimerLivre(@PathVariable int id) {
        livreRepository.deleteById(id);
        return "Livre supprimé.";
    }
}