package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void run(String... args) throws Exception {

        // Créer les utilisateurs par défaut
        if (utilisateurRepository.count() == 0) {
            utilisateurRepository.save(new Utilisateur(
                "admin", "admin123", "admin@bibliotheque.com",
                "Paris", "France", "TRAVAILLEUR", "ROLE_ADMIN"));
            utilisateurRepository.save(new Utilisateur(
                "user", "user123", "user@bibliotheque.com",
                "Lyon", "France", "ETUDIANT", "ROLE_USER"));
            System.out.println("Utilisateurs créés : admin et user");
        }

        // Ne pas ajouter si des livres existent déjà
        if (livreRepository.count() > 0) {
            System.out.println("Des données existent déjà, pas d'initialisation.");
            return;
        }

        System.out.println("Initialisation des données...");

        // Créer des catégories
        Categorie roman = categorieRepository.save(
                new Categorie("Roman", "Œuvres de fiction narrative"));
        Categorie scienceFiction = categorieRepository.save(
                new Categorie("Science-fiction", "Littérature d'anticipation"));
        Categorie philosophie = categorieRepository.save(
                new Categorie("Philosophie", "Œuvres philosophiques"));
        Categorie histoire = categorieRepository.save(
                new Categorie("Histoire", "Œuvres historiques"));
        Categorie poesie = categorieRepository.save(
                new Categorie("Poésie", "Œuvres poétiques"));

        Categorie[] categories = {roman, scienceFiction, philosophie, histoire, poesie};

        // Créer des auteurs
        Auteur a1 = auteurRepository.save(new Auteur("Saint-Exupéry", "Française", 1900));
        Auteur a2 = auteurRepository.save(new Auteur("Paulo Coelho", "Brésilienne", 1947));
        Auteur a3 = auteurRepository.save(new Auteur("Émile Zola", "Française", 1840));
        Auteur a4 = auteurRepository.save(new Auteur("Victor Hugo", "Française", 1802));
        Auteur a5 = auteurRepository.save(new Auteur("Albert Camus", "Française", 1913));

        Auteur[] auteurs = {a1, a2, a3, a4, a5};
        String[] titres = {
            "Le Petit Prince", "L'Alchimiste", "Germinal",
            "Les Misérables", "L'Étranger"
        };

        // Créer 200 livres avec catégories
        for (int i = 0; i < 200; i++) {
            Auteur auteur = auteurs[i % auteurs.length];
            Categorie categorie = categories[i % categories.length];
            String titre = titres[i % titres.length] + " - Tome " + (i + 1);
            int annee = 1800 + (i % 200);
            livreRepository.save(new Livre(titre, annee, auteur, categorie));
        }

        System.out.println("5 catégories, 5 auteurs et 200 livres ajoutés avec succès !");
    }
}