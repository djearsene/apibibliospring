package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatistiquesTest {

  @Mock
  private LivreRepository livreRepository;

  @Mock
  private AuteurRepository auteurRepository;

  @Mock
  private CategorieRepository categorieRepository;

  @Mock
  private UtilisateurRepository utilisateurRepository;

  @InjectMocks
  private StatistiquesController statistiquesController;

  // Test 1 : les statistiques retournent un résultat non vide
  @Test
  void getStatistiques_retourneResultatNonVide() {
    when(livreRepository.count()).thenReturn(200L);
    when(auteurRepository.count()).thenReturn(5L);
    when(categorieRepository.count()).thenReturn(5L);
    when(utilisateurRepository.count()).thenReturn(2L);
    when(categorieRepository.findAll()).thenReturn(List.of());
    when(auteurRepository.findAll()).thenReturn(List.of());
    when(utilisateurRepository.findAll()).thenReturn(List.of());

    ResponseEntity<Map<String, Object>> response = statistiquesController.getStatistiques();

    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
  }

  // Test 2 : le total des livres est correct
  @Test
  void getStatistiques_retourneTotalLivresCorrect() {
    when(livreRepository.count()).thenReturn(200L);
    when(auteurRepository.count()).thenReturn(5L);
    when(categorieRepository.count()).thenReturn(5L);
    when(utilisateurRepository.count()).thenReturn(2L);
    when(categorieRepository.findAll()).thenReturn(List.of());
    when(auteurRepository.findAll()).thenReturn(List.of());
    when(utilisateurRepository.findAll()).thenReturn(List.of());

    ResponseEntity<Map<String, Object>> response = statistiquesController.getStatistiques();

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals(200L, body.get("totalLivres"));
  }

  // Test 3 : le total des auteurs est correct
  @Test
  void getStatistiques_retourneTotalAuteursCorrect() {
    when(livreRepository.count()).thenReturn(200L);
    when(auteurRepository.count()).thenReturn(5L);
    when(categorieRepository.count()).thenReturn(5L);
    when(utilisateurRepository.count()).thenReturn(2L);
    when(categorieRepository.findAll()).thenReturn(List.of());
    when(auteurRepository.findAll()).thenReturn(List.of());
    when(utilisateurRepository.findAll()).thenReturn(List.of());

    ResponseEntity<Map<String, Object>> response = statistiquesController.getStatistiques();

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals(5L, body.get("totalAuteurs"));
  }

  // Test 4 : les statistiques contiennent les bonnes clés
  @Test
  void getStatistiques_contientBonnesCles() {
    when(livreRepository.count()).thenReturn(100L);
    when(auteurRepository.count()).thenReturn(3L);
    when(categorieRepository.count()).thenReturn(3L);
    when(utilisateurRepository.count()).thenReturn(1L);
    when(categorieRepository.findAll()).thenReturn(List.of());
    when(auteurRepository.findAll()).thenReturn(List.of());
    when(utilisateurRepository.findAll()).thenReturn(List.of());

    ResponseEntity<Map<String, Object>> response = statistiquesController.getStatistiques();

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertTrue(body.containsKey("totalLivres"));
    assertTrue(body.containsKey("totalAuteurs"));
    assertTrue(body.containsKey("totalCategories"));
    assertTrue(body.containsKey("totalUtilisateurs"));
    assertTrue(body.containsKey("livresParCategorie"));
    assertTrue(body.containsKey("livresParAuteur"));
    assertTrue(body.containsKey("utilisateursParStatut"));
  }

  // Test 5 : statistiques des utilisateurs par statut
  @Test
  void getStatistiques_compteBienUtilisateursParStatut() {
    Utilisateur etudiant = new Utilisateur(
        "user1", "pass", "user1@test.com",
        "Paris", "France", "ETUDIANT", "ROLE_USER");
    Utilisateur travailleur = new Utilisateur(
        "user2", "pass", "user2@test.com",
        "Lyon", "France", "TRAVAILLEUR", "ROLE_USER");

    when(livreRepository.count()).thenReturn(0L);
    when(auteurRepository.count()).thenReturn(0L);
    when(categorieRepository.count()).thenReturn(0L);
    when(utilisateurRepository.count()).thenReturn(2L);
    when(categorieRepository.findAll()).thenReturn(List.of());
    when(auteurRepository.findAll()).thenReturn(List.of());
    when(utilisateurRepository.findAll()).thenReturn(List.of(etudiant, travailleur));

    ResponseEntity<Map<String, Object>> response = statistiquesController.getStatistiques();

    Map<String, Object> body = response.getBody();
    assertNotNull(body);

    @SuppressWarnings("unchecked")
    Map<String, Long> statsStatut = (Map<String, Long>) body.get("utilisateursParStatut");

    assertEquals(1L, statsStatut.get("ETUDIANT"));
    assertEquals(1L, statsStatut.get("TRAVAILLEUR"));
  }
}