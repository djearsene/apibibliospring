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

    // Constructeur vide
    public Auteur() {}

    // Constructeur avec paramètres
    public Auteur(String nom, String nationalite, int anneeNaissance) {
        this.nom = nom;
        this.nationalite = nationalite;
        this.anneeNaissance = anneeNaissance;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getNationalite() { return nationalite; }
    public int getAnneeNaissance() { return anneeNaissance; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    public void setAnneeNaissance(int anneeNaissance) { this.anneeNaissance = anneeNaissance; }
}