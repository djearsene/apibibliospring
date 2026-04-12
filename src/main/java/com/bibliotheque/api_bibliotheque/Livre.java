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

    private String imageUrl;

    // Relation ManyToOne avec Auteur
    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    // Relation ManyToOne avec Categorie
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // Constructeur vide
    public Livre() {
    }

    // Constructeur sans image
    public Livre(String titre, int annee, Auteur auteur) {
        this.titre = titre;
        this.annee = annee;
        this.auteur = auteur;
    }

    // Constructeur avec catégorie
    public Livre(String titre, int annee, Auteur auteur, Categorie categorie) {
        this.titre = titre;
        this.annee = annee;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    // Constructeur avec image et catégorie
    public Livre(String titre, int annee, Auteur auteur,
            Categorie categorie, String imageUrl) {
        this.titre = titre;
        this.annee = annee;
        this.auteur = auteur;
        this.categorie = categorie;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getAnnee() {
        return annee;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}