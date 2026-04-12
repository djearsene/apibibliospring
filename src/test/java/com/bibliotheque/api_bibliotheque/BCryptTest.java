package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

public class BCryptTest {

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  // Test 1 : hacher un mot de passe retourne une valeur non nulle
  @Test
  void hacher_motDePasse_retourneValeurNonNulle() {
    String motDePasse = "admin123";
    String hachage = passwordEncoder.encode(motDePasse);
    assertNotNull(hachage);
    assertFalse(hachage.isEmpty());
  }

  // Test 2 : le hachage est différent du mot de passe original
  @Test
  void hachage_estDifferentDuMotDePasseOriginal() {
    String motDePasse = "admin123";
    String hachage = passwordEncoder.encode(motDePasse);
    assertNotEquals(motDePasse, hachage);
  }

  // Test 3 : vérifier un mot de passe correct retourne true
  @Test
  void verifier_motDePasseCorrect_retourneTrue() {
    String motDePasse = "admin123";
    String hachage = passwordEncoder.encode(motDePasse);
    assertTrue(passwordEncoder.matches(motDePasse, hachage));
  }

  // Test 4 : vérifier un mot de passe incorrect retourne false
  @Test
  void verifier_motDePasseIncorrect_retourneFalse() {
    String motDePasse = "admin123";
    String hachage = passwordEncoder.encode(motDePasse);
    assertFalse(passwordEncoder.matches("mauvaisMotDePasse", hachage));
  }

  // Test 5 : deux hachages du même mot de passe sont différents
  @Test
  void deuxHachages_memeMdp_sontDifferents() {
    String motDePasse = "admin123";
    String hachage1 = passwordEncoder.encode(motDePasse);
    String hachage2 = passwordEncoder.encode(motDePasse);
    assertNotEquals(hachage1, hachage2);
    assertTrue(passwordEncoder.matches(motDePasse, hachage1));
    assertTrue(passwordEncoder.matches(motDePasse, hachage2));
  }

  // Test 6 : le hachage commence par $2a$
  @Test
  void hachage_commenceParPrefixeBCrypt() {
    String motDePasse = "user123";
    String hachage = passwordEncoder.encode(motDePasse);
    assertTrue(hachage.startsWith("$2a$"));
  }

  // Test 7 : hacher un mot de passe vide ne lance pas d'exception
  @Test
  void hacher_motDePasseVide_neGenesPasException() {
    String hachage = passwordEncoder.encode("");
    assertNotNull(hachage);
    assertTrue(passwordEncoder.matches("", hachage));
  }

  // Test 8 : l'utilisateur admin a un mot de passe haché valide
  @Test
  void utilisateurAdmin_motDePasseHache_estValide() {
    String motDePasseAdmin = "admin123";
    String hachage = passwordEncoder.encode(motDePasseAdmin);

    Utilisateur admin = new Utilisateur(
        "admin", hachage, "admin@bibliotheque.com",
        "Paris", "France", "TRAVAILLEUR", "ROLE_ADMIN");

    assertTrue(passwordEncoder.matches(motDePasseAdmin, admin.getPassword()));
    assertFalse(passwordEncoder.matches("mauvaisMotDePasse", admin.getPassword()));
  }
}