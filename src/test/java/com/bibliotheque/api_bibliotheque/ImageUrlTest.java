package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageUrlTest {

  // Test 1 : créer un auteur avec une image
  @Test
  void creerAuteur_avecImageUrl() {
    Auteur auteur = new Auteur(
        "Victor Hugo", "Française", 1802,
        "https://exemple.com/victor-hugo.jpg");

    assertEquals("Victor Hugo", auteur.getNom());
    assertEquals("https://exemple.com/victor-hugo.jpg", auteur.getImageUrl());
  }

  // Test 2 : créer un auteur sans image retourne null
  @Test
  void creerAuteur_sansImage_retourneNull() {
    Auteur auteur = new Auteur("Zola", "Française", 1840);
    assertNull(auteur.getImageUrl());
  }

  // Test 3 : modifier l'image d'un auteur
  @Test
  void modifierImageAuteur_metAJourImageUrl() {
    Auteur auteur = new Auteur("Hugo", "Française", 1802);
    assertNull(auteur.getImageUrl());

    auteur.setImageUrl("https://exemple.com/hugo.jpg");
    assertEquals("https://exemple.com/hugo.jpg", auteur.getImageUrl());
  }

  // Test 4 : créer un livre avec une image
  @Test
  void creerLivre_avecImageUrl() {
    Auteur auteur = new Auteur("Hugo", "Française", 1802);
    Categorie categorie = new Categorie("Roman", "Fiction narrative");
    Livre livre = new Livre(
        "Les Misérables", 1862, auteur,
        categorie, "https://exemple.com/les-miserables.jpg");

    assertEquals("Les Misérables", livre.getTitre());
    assertEquals("https://exemple.com/les-miserables.jpg", livre.getImageUrl());
  }

  // Test 5 : créer un livre sans image retourne null
  @Test
  void creerLivre_sansImage_retourneNull() {
    Auteur auteur = new Auteur("Zola", "Française", 1840);
    Livre livre = new Livre("Germinal", 1885, auteur);
    assertNull(livre.getImageUrl());
  }

  // Test 6 : modifier l'image d'un livre
  @Test
  void modifierImageLivre_metAJourImageUrl() {
    Auteur auteur = new Auteur("Camus", "Française", 1913);
    Livre livre = new Livre("L'Étranger", 1942, auteur);
    assertNull(livre.getImageUrl());

    livre.setImageUrl("https://exemple.com/letranger.jpg");
    assertEquals("https://exemple.com/letranger.jpg", livre.getImageUrl());
  }

  // Test 7 : l'URL de l'image peut être modifiée
  @Test
  void modifierImageUrl_remplaceLAncienneUrl() {
    Auteur auteur = new Auteur(
        "Proust", "Française", 1871,
        "https://exemple.com/ancienne-image.jpg");

    auteur.setImageUrl("https://exemple.com/nouvelle-image.jpg");
    assertEquals("https://exemple.com/nouvelle-image.jpg", auteur.getImageUrl());
  }

  // Test 8 : livre et auteur peuvent avoir des images différentes
  @Test
  void livreEtAuteur_peuventAvoirImagesDistinctes() {
    Auteur auteur = new Auteur(
        "Hugo", "Française", 1802,
        "https://exemple.com/hugo.jpg");
    Categorie categorie = new Categorie("Roman", "Fiction");
    Livre livre = new Livre(
        "Les Misérables", 1862, auteur,
        categorie, "https://exemple.com/miserables.jpg");

    assertNotEquals(auteur.getImageUrl(), livre.getImageUrl());
    assertEquals("https://exemple.com/hugo.jpg", auteur.getImageUrl());
    assertEquals("https://exemple.com/miserables.jpg", livre.getImageUrl());
  }
}