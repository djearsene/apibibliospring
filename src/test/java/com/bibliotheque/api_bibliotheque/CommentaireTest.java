package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class CommentaireTest {

  // Test 1 : créer un commentaire avec tous les champs
  @Test
  void creerCommentaire_avecTousLesChamps() {
    Auteur auteur = new Auteur("Hugo", "Française", 1802);
    Livre livre = new Livre("Les Misérables", 1862, auteur);
    LocalDateTime maintenant = LocalDateTime.now();

    Commentaire commentaire = new Commentaire(
        "Excellent livre !", "user", maintenant, livre);

    assertEquals("Excellent livre !", commentaire.getContenu());
    assertEquals("user", commentaire.getUsername());
    assertEquals(maintenant, commentaire.getDateCommentaire());
    assertNotNull(commentaire.getLivre());
  }

  // Test 2 : constructeur vide
  @Test
  void constructeurVide_creerCommentaireSansValeurs() {
    Commentaire commentaire = new Commentaire();
    assertNull(commentaire.getContenu());
    assertNull(commentaire.getUsername());
    assertNull(commentaire.getDateCommentaire());
    assertNull(commentaire.getLivre());
  }

  // Test 3 : modifier le contenu d'un commentaire
  @Test
  void modifierCommentaire_metAJourLeContenu() {
    Commentaire commentaire = new Commentaire();
    commentaire.setContenu("Nouveau commentaire");
    assertEquals("Nouveau commentaire", commentaire.getContenu());
  }

  // Test 4 : un commentaire est lié à un livre
  @Test
  void commentaire_estLieAUnLivre() {
    Auteur auteur = new Auteur("Zola", "Française", 1840);
    Livre livre = new Livre("Germinal", 1885, auteur);
    LocalDateTime maintenant = LocalDateTime.now();

    Commentaire commentaire = new Commentaire(
        "Très bon livre !", "admin", maintenant, livre);

    assertNotNull(commentaire.getLivre());
    assertEquals("Germinal", commentaire.getLivre().getTitre());
  }

  // Test 5 : deux commentaires ont des dates différentes
  @Test
  void deuxCommentaires_ontDatesDifferentes() throws InterruptedException {
    Auteur auteur = new Auteur("Camus", "Française", 1913);
    Livre livre = new Livre("L'Étranger", 1942, auteur);

    LocalDateTime date1 = LocalDateTime.now();
    Thread.sleep(10);
    LocalDateTime date2 = LocalDateTime.now();

    Commentaire c1 = new Commentaire("Commentaire 1", "user", date1, livre);
    Commentaire c2 = new Commentaire("Commentaire 2", "user", date2, livre);

    assertNotEquals(c1.getDateCommentaire(), c2.getDateCommentaire());
    assertTrue(c2.getDateCommentaire().isAfter(c1.getDateCommentaire()));
  }

  // Test 6 : plusieurs commentaires peuvent être sur le même livre
  @Test
  void plusieursCommentaires_surMemeLivre() {
    Auteur auteur = new Auteur("Hugo", "Française", 1802);
    Livre livre = new Livre("Les Misérables", 1862, auteur);
    LocalDateTime maintenant = LocalDateTime.now();

    Commentaire c1 = new Commentaire("Super !", "user1", maintenant, livre);
    Commentaire c2 = new Commentaire("Magnifique !", "user2", maintenant, livre);

    assertEquals(c1.getLivre().getTitre(), c2.getLivre().getTitre());
    assertNotEquals(c1.getUsername(), c2.getUsername());
  }

  // Test 7 : modifier le livre d'un commentaire
  @Test
  void modifierLivreDuCommentaire_metAJourLeLivre() {
    Auteur auteur = new Auteur("Zola", "Française", 1840);
    Livre livre1 = new Livre("Germinal", 1885, auteur);
    Livre livre2 = new Livre("Nana", 1880, auteur);
    LocalDateTime maintenant = LocalDateTime.now();

    Commentaire commentaire = new Commentaire(
        "Bon livre", "user", maintenant, livre1);
    assertEquals("Germinal", commentaire.getLivre().getTitre());

    commentaire.setLivre(livre2);
    assertEquals("Nana", commentaire.getLivre().getTitre());
  }
}