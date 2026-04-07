package com.bibliotheque.api_bibliotheque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {

    // Recherche par titre
    Page<Livre> findByTitreContainingIgnoreCase(String titre, Pageable pageable);

    // Recherche par nom de l'auteur
    Page<Livre> findByAuteurNomContainingIgnoreCase(String nom, Pageable pageable);

    // Recherche par catégorie
    Page<Livre> findByCategorieNomContainingIgnoreCase(String nom, Pageable pageable);
}