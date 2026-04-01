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

    @Override
    public void run(String... args) throws Exception {

        if (livreRepository.count() > 0) {
            System.out.println("Des données existent déjà, pas d'initialisation.");
            return;
        }

        System.out.println("Initialisation des données...");

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

        // Créer 200 livres
        for (int i = 0; i < 200; i++) {
            Auteur auteur = auteurs[i % auteurs.length];
            String titre = titres[i % titres.length] + " - Tome " + (i + 1);
            int annee = 1800 + (i % 200);
            livreRepository.save(new Livre(titre, annee, auteur));
        }

        System.out.println("5 auteurs et 200 livres ajoutés avec succès !");
    }
}