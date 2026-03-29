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

  @NotBlank(message = "L'auteur est obligatoire.")
  private String auteur;

  @Min(value = 1000, message = "L'année doit être supérieure à 1000.")
  @Max(value = 2026, message = "L'année ne peut pas dépasser 2026.")
  private int annee;

  // Constructeur vide obligatoire pour JPA
  public Livre() {
  }

  // Constructeur avec paramètres
  public Livre(String titre, String auteur, int annee) {
    this.titre = titre;
    this.auteur = auteur;
    this.annee = annee;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getTitre() {
    return titre;
  }

  public String getAuteur() {
    return auteur;
  }

  public int getAnnee() {
    return annee;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  public void setAnnee(int annee) {
    this.annee = annee;
  }
}