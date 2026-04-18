package com.bibliotheque.api_bibliotheque;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentaires")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le contenu est obligatoire.")
    private String contenu;

    private String username;
    private LocalDateTime dateCommentaire;

    // Relation ManyToOne avec Livre
    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    // Constructeur vide
    public Commentaire() {}

    // Constructeur avec paramètres
    public Commentaire(String contenu, String username,
                       LocalDateTime dateCommentaire, Livre livre) {
        this.contenu = contenu;
        this.username = username;
        this.dateCommentaire = dateCommentaire;
        this.livre = livre;
    }

    // Getters
    public int getId() { return id; }
    public String getContenu() { return contenu; }
    public String getUsername() { return username; }
    public LocalDateTime getDateCommentaire() { return dateCommentaire; }
    public Livre getLivre() { return livre; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    public void setUsername(String username) { this.username = username; }
    public void setDateCommentaire(LocalDateTime dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }
    public void setLivre(Livre livre) { this.livre = livre; }
}