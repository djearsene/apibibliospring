package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurTest {

    // Test 1 : créer un utilisateur admin avec tous les champs
    @Test
    void creerUtilisateur_admin_avecTousLesChamps() {
        Utilisateur admin = new Utilisateur(
                "admin", "admin123", "admin@bibliotheque.com",
                "Paris", "France", "TRAVAILLEUR", "ROLE_ADMIN");

        assertEquals("admin", admin.getUsername());
        assertEquals("admin123", admin.getPassword());
        assertEquals("admin@bibliotheque.com", admin.getEmail());
        assertEquals("Paris", admin.getVille());
        assertEquals("France", admin.getPays());
        assertEquals("TRAVAILLEUR", admin.getStatut());
        assertEquals("ROLE_ADMIN", admin.getRole());
    }

    // Test 2 : créer un utilisateur avec le rôle USER
    @Test
    void creerUtilisateur_user_avecRoleUser() {
        Utilisateur user = new Utilisateur(
                "user", "user123", "user@bibliotheque.com",
                "Lyon", "France", "ETUDIANT", "ROLE_USER");

        assertEquals("ROLE_USER", user.getRole());
        assertEquals("ETUDIANT", user.getStatut());
    }

    // Test 3 : vérifier les statuts possibles
    @Test
    void creerUtilisateur_avecDifferentsStatuts() {
        Utilisateur eleve = new Utilisateur(
                "eleve", "pass", "eleve@test.com",
                "Paris", "France", "ELEVE", "ROLE_USER");

        Utilisateur etudiant = new Utilisateur(
                "etudiant", "pass", "etudiant@test.com",
                "Lyon", "France", "ETUDIANT", "ROLE_USER");

        Utilisateur travailleur = new Utilisateur(
                "travailleur", "pass", "travailleur@test.com",
                "Marseille", "France", "TRAVAILLEUR", "ROLE_USER");

        Utilisateur autre = new Utilisateur(
                "autre", "pass", "autre@test.com",
                "Nice", "France", "AUTRE", "ROLE_USER");

        assertEquals("ELEVE", eleve.getStatut());
        assertEquals("ETUDIANT", etudiant.getStatut());
        assertEquals("TRAVAILLEUR", travailleur.getStatut());
        assertEquals("AUTRE", autre.getStatut());
    }

    // Test 4 : modifier les champs d'un utilisateur
    @Test
    void modifierUtilisateur_metAJourLesChamps() {
        Utilisateur user = new Utilisateur();
        user.setUsername("kouadio");
        user.setEmail("kouadio@exemple.com");
        user.setVille("Abidjan");
        user.setPays("Côte d'Ivoire");
        user.setStatut("ETUDIANT");
        user.setRole("ROLE_USER");

        assertEquals("kouadio", user.getUsername());
        assertEquals("Abidjan", user.getVille());
        assertEquals("Côte d'Ivoire", user.getPays());
        assertEquals("ETUDIANT", user.getStatut());
    }

    // Test 5 : vérifier que admin et user ont des rôles différents
    @Test
    void admin_et_user_ontRolesDifferents() {
        Utilisateur admin = new Utilisateur(
                "admin", "admin123", "admin@test.com",
                "Paris", "France", "TRAVAILLEUR", "ROLE_ADMIN");

        Utilisateur user = new Utilisateur(
                "user", "user123", "user@test.com",
                "Lyon", "France", "ETUDIANT", "ROLE_USER");

        assertNotEquals(admin.getRole(), user.getRole());
        assertEquals("ROLE_ADMIN", admin.getRole());
        assertEquals("ROLE_USER", user.getRole());
    }

    // Test 6 : vérifier le constructeur vide
    @Test
    void constructeurVide_creerUtilisateurSansValeurs() {
        Utilisateur user = new Utilisateur();
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getRole());
    }
}