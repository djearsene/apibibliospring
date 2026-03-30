package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LivreRepository livreRepository;

    // Listes de données fictives
    private String[] titres = {
        "Le Petit Prince", "L'Alchimiste", "Germinal", "Les Misérables",
        "Notre-Dame de Paris", "Madame Bovary", "Le Rouge et le Noir",
        "L'Étranger", "La Peste", "Le Comte de Monte-Cristo",
        "Voyage au bout de la nuit", "À la recherche du temps perdu",
        "Les Fleurs du mal", "Candide", "Zadig", "L'Ingénu",
        "Robinson Crusoé", "Gulliver", "Don Quichotte", "Faust"
    };

    private String[] auteurs = {
        "Saint-Exupéry", "Paulo Coelho", "Émile Zola", "Victor Hugo",
        "Victor Hugo", "Gustave Flaubert", "Stendhal",
        "Albert Camus", "Albert Camus", "Alexandre Dumas",
        "Louis-Ferdinand Céline", "Marcel Proust",
        "Charles Baudelaire", "Voltaire", "Voltaire", "Voltaire",
        "Daniel Defoe", "Jonathan Swift", "Cervantes", "Goethe"
    };

    @Override
    public void run(String... args) throws Exception {

        // Ne pas ajouter si des livres existent déjà
        if (livreRepository.count() > 0) {
            System.out.println("Des livres existent déjà, pas d'initialisation.");
            return;
        }

        System.out.println("Initialisation des données : ajout de 200 livres...");

        for (int i = 0; i < 200; i++) {
            // Choisir un titre et auteur de façon cyclique
            String titre = titres[i % titres.length] + " - Tome " + (i + 1);
            String auteur = auteurs[i % auteurs.length];
            int annee = 1800 + (i % 200);

            Livre livre = new Livre(titre, auteur, annee);
            livreRepository.save(livre);
        }

        System.out.println("200 livres ajoutés avec succès !");
    }
}