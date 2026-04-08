package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategorieTest {

  // Test 1 : créer une catégorie avec tous les champs
  @Test
  void creerCategorie_avecTousLesChamps() {
    Categorie categorie = new Categorie("Roman", "Œuvres de fiction narrative");

    assertEquals("Roman", categorie.getNom());
    assertEquals("Œuvres de fiction narrative", categorie.getDescription());
  }

  // Test 2 : constructeur vide
  @Test
  void constructeurVide_creerCategorieSansValeurs() {
    Categorie categorie = new Categorie();
    assertNull(categorie.getNom());
    assertNull(categorie.getDescription());
  }

  // Test 3 : modifier les champs d'une catégorie
  @Test
  void modifierCategorie_metAJourLesChamps() {
    Categorie categorie = new Categorie();
    categorie.setNom("Science-fiction");
    categorie.setDescription("Littérature d'anticipation");

    assertEquals("Science-fiction", categorie.getNom());
    assertEquals("Littérature d'anticipation", categorie.getDescription());
  }

  // Test 4 : deux catégories différentes ont des noms différents
  @Test
  void deuxCategories_ontNomsDifferents() {
    Categorie roman = new Categorie("Roman", "Fiction narrative");
    Categorie poesie = new Categorie("Poésie", "Œuvres poétiques");

    assertNotEquals(roman.getNom(), poesie.getNom());
  }

  // Test 5 : un livre peut avoir une catégorie
  @Test
  void livre_peutAvoirUneCategorie() {
    Categorie categorie = new Categorie("Histoire", "Œuvres historiques");
    Auteur auteur = new Auteur("Zola", "Française", 1840);
    Livre livre = new Livre("Germinal", 1885, auteur, categorie);

    assertNotNull(livre.getCategorie());
    assertEquals("Histoire", livre.getCategorie().getNom());
  }

  // Test 6 : un livre sans catégorie retourne null
  @Test
  void livre_sansCategorie_retourneNull() {
    Auteur auteur = new Auteur("Hugo", "Française", 1802);
    Livre livre = new Livre("Les Misérables", 1862, auteur);

    assertNull(livre.getCategorie());
  }

  // Test 7 : modifier la catégorie d'un livre
  @Test
  void modifierCategorieDuLivre_metAJourLaCategorie() {
    Categorie roman = new Categorie("Roman", "Fiction narrative");
    Categorie philosophie = new Categorie("Philosophie", "Œuvres philosophiques");
    Auteur auteur = new Auteur("Camus", "Française", 1913);
    Livre livre = new Livre("L'Étranger", 1942, auteur, roman);

    assertEquals("Roman", livre.getCategorie().getNom());

    livre.setCategorie(philosophie);
    assertEquals("Philosophie", livre.getCategorie().getNom());
  }
}