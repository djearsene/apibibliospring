package com.bibliotheque.api_bibliotheque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {

  // Recherche par titre (insensible à la casse)
  Page<Livre> findByTitreContainingIgnoreCase(String titre, Pageable pageable);

  // Recherche par auteur (insensible à la casse)
  Page<Livre> findByAuteurContainingIgnoreCase(String auteur, Pageable pageable);
}