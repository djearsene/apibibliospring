package com.bibliotheque.api_bibliotheque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
  Optional<Auteur> findByNom(String nom);
}