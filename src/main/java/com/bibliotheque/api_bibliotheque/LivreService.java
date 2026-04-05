package com.bibliotheque.api_bibliotheque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    // Créer le logger pour cette classe
    private static final Logger logger = LoggerFactory.getLogger(LivreService.class);

    @Autowired
    private LivreRepository livreRepository;

    // Récupérer tous les livres avec pagination
    public Page<Livre> getTousLesLivres(Pageable pageable) {
        logger.info("Récupération de tous les livres - page {} taille {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return livreRepository.findAll(pageable);
    }

    // Récupérer un livre par son id
    public Livre getLivreParId(int id) {
        logger.debug("Recherche du livre avec l'id {}", id);
        Optional<Livre> livre = livreRepository.findById(id);
        if (livre.isPresent()) {
            logger.info("Livre trouvé : {}", livre.get().getTitre());
            return livre.get();
        }
        logger.warn("Livre non trouvé avec l'id {}", id);
        throw new LivreNotFoundException(id);
    }

    // Rechercher par titre
    public Page<Livre> rechercherParTitre(String titre, Pageable pageable) {
        logger.info("Recherche par titre : '{}'", titre);
        return livreRepository.findByTitreContainingIgnoreCase(titre, pageable);
    }

    // Rechercher par auteur
    public Page<Livre> rechercherParAuteur(String auteur, Pageable pageable) {
        logger.info("Recherche par auteur : '{}'", auteur);
        return livreRepository.findByAuteurContainingIgnoreCase(auteur, pageable);
    }

    // Ajouter un livre
    public Livre ajouterLivre(Livre livre) {
        logger.info("Ajout d'un nouveau livre : '{}'", livre.getTitre());
        Livre sauvegarde = livreRepository.save(livre);
        logger.info("Livre ajouté avec succès, id : {}", sauvegarde.getId());
        return sauvegarde;
    }

    // Modifier un livre
    public Livre modifierLivre(int id, Livre livreModifie) {
        logger.info("Modification du livre avec l'id {}", id);
        Livre livre = getLivreParId(id);
        livre.setTitre(livreModifie.getTitre());
        livre.setAuteur(livreModifie.getAuteur());
        livre.setAnnee(livreModifie.getAnnee());
        Livre sauvegarde = livreRepository.save(livre);
        logger.info("Livre modifié avec succès : '{}'", sauvegarde.getTitre());
        return sauvegarde;
    }

    // Supprimer un livre
    public void supprimerLivre(int id) {
        logger.info("Suppression du livre avec l'id {}", id);
        if (!livreRepository.existsById(id)) {
            logger.warn("Tentative de suppression d'un livre inexistant, id {}", id);
            throw new LivreNotFoundException(id);
        }
        livreRepository.deleteById(id);
        logger.info("Livre supprimé avec succès, id {}", id);
    }

    // Exporter en CSV
    public String exporterCSV() {
        logger.info("Export CSV de tous les livres");
        List<Livre> livres = livreRepository.findAll();
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Titre,Auteur,Année\n");
        for (Livre livre : livres) {
            String nomAuteur = livre.getAuteur() != null ? livre.getAuteur().getNom() : "Inconnu";
            csv.append(livre.getId()).append(",");
            csv.append("\"").append(livre.getTitre()).append("\"").append(",");
            csv.append("\"").append(nomAuteur).append("\"").append(",");
            csv.append(livre.getAnnee()).append("\n");
        }
        logger.info("Export CSV terminé : {} livres exportés", livres.size());
        return csv.toString();
    }
}