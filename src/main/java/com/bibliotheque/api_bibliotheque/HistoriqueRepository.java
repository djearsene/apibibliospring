package com.bibliotheque.api_bibliotheque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Integer> {

  // Trouver l'historique par username
  List<Historique> findByUsernameOrderByDateActionDesc(String username);

  // Trouver l'historique par action
  List<Historique> findByActionOrderByDateActionDesc(String action);

  // Trouver l'historique par titre de livre
  List<Historique> findByTitreLivreContainingIgnoreCaseOrderByDateActionDesc(String titre);
}