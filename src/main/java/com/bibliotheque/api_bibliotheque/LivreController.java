package com.bibliotheque.api_bibliotheque;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {

  private List<Livre> livres = new ArrayList<>();
  private int compteur = 1;

  // GET /livres : retourne tous les livres
  @GetMapping
  public List<Livre> getTousLesLivres() {
    return livres;
  }

  // GET /livres/{id} : retourne un livre par son id
  @GetMapping("/{id}")
  public Livre getLivreParId(@PathVariable int id) {
    for (Livre l : livres) {
      if (l.getId() == id) {
        return l;
      }
    }
    return null;
  }

  // POST /livres : ajouter un nouveau livre
  @PostMapping
  public Livre ajouterLivre(@RequestBody Livre livre) {
    livre.setId(compteur++);
    livres.add(livre);
    return livre;
  }

  // DELETE /livres/{id} : supprimer un livre
  @DeleteMapping("/{id}")
  public String supprimerLivre(@PathVariable int id) {
    livres.removeIf(l -> l.getId() == id);
    return "Livre supprimé.";
  }
}