package com.bibliotheque.api_bibliotheque;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique")
public class Historique {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String action;
  private String titreLivre;
  private String username;
  private LocalDateTime dateAction;

  // Constructeur vide
  public Historique() {
  }

  // Constructeur avec paramètres
  public Historique(String action, String titreLivre,
      String username, LocalDateTime dateAction) {
    this.action = action;
    this.titreLivre = titreLivre;
    this.username = username;
    this.dateAction = dateAction;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getAction() {
    return action;
  }

  public String getTitreLivre() {
    return titreLivre;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getDateAction() {
    return dateAction;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setTitreLivre(String titreLivre) {
    this.titreLivre = titreLivre;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setDateAction(LocalDateTime dateAction) {
    this.dateAction = dateAction;
  }
}