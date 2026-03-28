package com.bibliotheque.api_bibliotheque;

public class Livre {

  private int id;
  private String titre;
  private String auteur;
  private int annee;

  // Constructeur
  public Livre(int id, String titre, String auteur, int annee) {
    this.id = id;
    this.titre = titre;
    this.auteur = auteur;
    this.annee = annee;
  }

  // Constructeur vide nécessaire pour Spring Boot
  public Livre() {
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