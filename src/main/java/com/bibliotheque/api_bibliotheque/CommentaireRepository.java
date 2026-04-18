package com.bibliotheque.api_bibliotheque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

    // Trouver les commentaires d'un livre
    List<Commentaire> findByLivreIdOrderByDateCommentaireDesc(int livreId);

    // Trouver les commentaires d'un utilisateur
    List<Commentaire> findByUsernameOrderByDateCommentaireDesc(String username);
}