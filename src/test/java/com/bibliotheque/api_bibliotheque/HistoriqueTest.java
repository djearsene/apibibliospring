package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class HistoriqueTest {

  // Test 1 : créer un historique avec tous les champs
  @Test
  void creerHistorique_avecTousLesChamps() {
    LocalDateTime maintenant = LocalDateTime.now();
    Historique historique = new Historique(
        "CREATION", "Le Petit Prince", "admin", maintenant);

    assertEquals("CREATION", historique.getAction());
    assertEquals("Le Petit Prince", historique.getTitreLivre());
    assertEquals("admin", historique.getUsername());
    assertEquals(maintenant, historique.getDateAction());
  }

  // Test 2 : constructeur vide
  @Test
  void constructeurVide_creerHistoriqueSansValeurs() {
    Historique historique = new Historique();
    assertNull(historique.getAction());
    assertNull(historique.getTitreLivre());
    assertNull(historique.getUsername());
    assertNull(historique.getDateAction());
  }

  // Test 3 : modifier les champs d'un historique
  @Test
  void modifierHistorique_metAJourLesChamps() {
    Historique historique = new Historique();
    LocalDateTime maintenant = LocalDateTime.now();

    historique.setAction("MODIFICATION");
    historique.setTitreLivre("Germinal");
    historique.setUsername("user");
    historique.setDateAction(maintenant);

    assertEquals("MODIFICATION", historique.getAction());
    assertEquals("Germinal", historique.getTitreLivre());
    assertEquals("user", historique.getUsername());
    assertEquals(maintenant, historique.getDateAction());
  }

  // Test 4 : les trois types d'actions sont valides
  @Test
  void historique_troisTypesActionsPossibles() {
    LocalDateTime maintenant = LocalDateTime.now();

    Historique creation = new Historique(
        "CREATION", "Livre 1", "admin", maintenant);
    Historique modification = new Historique(
        "MODIFICATION", "Livre 2", "admin", maintenant);
    Historique suppression = new Historique(
        "SUPPRESSION", "Livre 3", "admin", maintenant);

    assertEquals("CREATION", creation.getAction());
    assertEquals("MODIFICATION", modification.getAction());
    assertEquals("SUPPRESSION", suppression.getAction());
  }

  // Test 5 : deux historiques ont des dates différentes
  @Test
  void deuxHistoriques_ontDatesDifferentes() throws InterruptedException {
    LocalDateTime date1 = LocalDateTime.now();
    Thread.sleep(10);
    LocalDateTime date2 = LocalDateTime.now();

    Historique h1 = new Historique("CREATION", "Livre 1", "admin", date1);
    Historique h2 = new Historique("CREATION", "Livre 2", "admin", date2);

    assertNotEquals(h1.getDateAction(), h2.getDateAction());
    assertTrue(h2.getDateAction().isAfter(h1.getDateAction()));
  }

  // Test 6 : l'action IMPORT_CSV est valide
  @Test
  void historique_actionImportCSVEstValide() {
    LocalDateTime maintenant = LocalDateTime.now();
    Historique historique = new Historique(
        "IMPORT_CSV", "Dracula", "admin", maintenant);

    assertEquals("IMPORT_CSV", historique.getAction());
    assertEquals("Dracula", historique.getTitreLivre());
  }

  // Test 7 : username anonyme est valide
  @Test
  void historique_usernameAnonymeEstValide() {
    LocalDateTime maintenant = LocalDateTime.now();
    Historique historique = new Historique(
        "CREATION", "Livre Test", "anonyme", maintenant);

    assertEquals("anonyme", historique.getUsername());
  }
}