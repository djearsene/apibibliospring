package com.bibliotheque.api_bibliotheque;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le titre est obligatoire.")
    private String titre;

    @Min(value = 1000, message = "L'année doit être supérieure à 1000.")
    @Max(value = 2026, message = "L'année ne peut pas dépasser 2026.")
    private int annee;

    // Relation ManyToOne : plusieurs livres pour un auteur
    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    // Constructeur vide
    public Livre() {}

    // Constructeur avec paramètres
    public Livre(String titre, int annee, Auteur auteur) {
        this.titre = titre;
        this.annee = annee;
        this.auteur = auteur;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public int getAnnee() { return annee; }
    public Auteur getAuteur() { return auteur; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setAnnee(int annee) { this.annee = annee; }
    public void setAuteur(Auteur auteur) { this.auteur = auteur; }
}