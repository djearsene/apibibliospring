package com.bibliotheque.api_bibliotheque;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/historique")
@Tag(name = "Historique", description = "API de l'historique des modifications")
public class HistoriqueController {

  @Autowired
  private HistoriqueRepository historiqueRepository;

  // GET /historique : voir tout l'historique
  @GetMapping
  @Operation(summary = "Voir tout l'historique des modifications")
  public ResponseEntity<List<Historique>> getToutLHistorique() {
    return ResponseEntity.ok(historiqueRepository
        .findAll(org.springframework.data.domain.Sort
            .by(org.springframework.data.domain.Sort.Direction.DESC,
                "dateAction")));
  }

  // GET /historique/utilisateur/{username}
  @GetMapping("/utilisateur/{username}")
  @Operation(summary = "Voir l'historique d'un utilisateur")
  public ResponseEntity<List<Historique>> getHistoriqueParUtilisateur(
      @PathVariable String username) {
    return ResponseEntity.ok(
        historiqueRepository.findByUsernameOrderByDateActionDesc(username));
  }

  // GET /historique/action/{action}
  @GetMapping("/action/{action}")
  @Operation(summary = "Voir l'historique par type d'action")
  public ResponseEntity<List<Historique>> getHistoriqueParAction(
      @PathVariable String action) {
    return ResponseEntity.ok(
        historiqueRepository.findByActionOrderByDateActionDesc(action));
  }

  // GET /historique/livre/{titre}
  @GetMapping("/livre/{titre}")
  @Operation(summary = "Voir l'historique d'un livre")
  public ResponseEntity<List<Historique>> getHistoriqueParLivre(
      @PathVariable String titre) {
    return ResponseEntity.ok(
        historiqueRepository
            .findByTitreLivreContainingIgnoreCaseOrderByDateActionDesc(titre));
  }
}