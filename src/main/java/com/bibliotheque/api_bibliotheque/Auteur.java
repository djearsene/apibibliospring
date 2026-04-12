package com.bibliotheque.api_bibliotheque;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "auteurs")
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le nom est obligatoire.")
    private String nom;

    private String nationalite;
    private int anneeNaissance;
    private String imageUrl;

    // Constructeur vide
    public Auteur() {
    }

    // Constructeur sans image
    public Auteur(String nom, String nationalite, int anneeNaissance) {
        this.nom = nom;
        this.nationalite = nationalite;
        this.anneeNaissance = anneeNaissance;
    }

    // Constructeur avec image
    public Auteur(String nom, String nationalite, int anneeNaissance, String imageUrl) {
        this.nom = nom;
        this.nationalite = nationalite;
        this.anneeNaissance = anneeNaissance;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setAnneeNaissance(int anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}