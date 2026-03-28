package com.bibliotheque.api_bibliotheque;

import jakarta.persistence.*;

@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private String auteur;
    private int annee;

    // Constructeur vide obligatoire pour JPA
    public Livre() {}

    // Constructeur avec paramètres
    public Livre(String titre, String auteur, int annee) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnnee() { return annee; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public void setAnnee(int annee) { this.annee = annee; }
}