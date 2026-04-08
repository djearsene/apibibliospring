package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistiques")
@Tag(name = "Statistiques", description = "API des statistiques de la bibliothèque")
public class StatistiquesController {

  @Autowired
  private LivreRepository livreRepository;

  @Autowired
  private AuteurRepository auteurRepository;

  @Autowired
  private CategorieRepository categorieRepository;

  @Autowired
  private UtilisateurRepository utilisateurRepository;

  // GET /statistiques
  @GetMapping
  @Operation(summary = "Obtenir les statistiques générales de la bibliothèque")
  public ResponseEntity<Map<String, Object>> getStatistiques() {
    Map<String, Object> stats = new HashMap<>();

    // Totaux généraux
    stats.put("totalLivres", livreRepository.count());
    stats.put("totalAuteurs", auteurRepository.count());
    stats.put("totalCategories", categorieRepository.count());
    stats.put("totalUtilisateurs", utilisateurRepository.count());

    // Livres par catégorie
    List<Categorie> categories = categorieRepository.findAll();
    Map<String, Long> livresParCategorie = new HashMap<>();
    for (Categorie categorie : categories) {
      long nombre = livreRepository
          .findByCategorieNomContainingIgnoreCase(
              categorie.getNom(),
              org.springframework.data.domain.PageRequest.of(0, Integer.MAX_VALUE))
          .getTotalElements();
      livresParCategorie.put(categorie.getNom(), nombre);
    }
    stats.put("livresParCategorie", livresParCategorie);

    // Livres par auteur
    List<Auteur> auteurs = auteurRepository.findAll();
    Map<String, Long> livresParAuteur = new HashMap<>();
    for (Auteur auteur : auteurs) {
      long nombre = livreRepository
          .findByAuteurNomContainingIgnoreCase(
              auteur.getNom(),
              org.springframework.data.domain.PageRequest.of(0, Integer.MAX_VALUE))
          .getTotalElements();
      livresParAuteur.put(auteur.getNom(), nombre);
    }
    stats.put("livresParAuteur", livresParAuteur);

    // Utilisateurs par statut
    Map<String, Long> utilisateursParStatut = new HashMap<>();
    List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
    for (Utilisateur utilisateur : utilisateurs) {
      String statut = utilisateur.getStatut() != null ? utilisateur.getStatut() : "INCONNU";
      utilisateursParStatut.merge(statut, 1L, Long::sum);
    }
    stats.put("utilisateursParStatut", utilisateursParStatut);

    return ResponseEntity.ok(stats);
  }
}