package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    // Récupérer tous les livres avec pagination
    public Page<Livre> getTousLesLivres(Pageable pageable) {
        return livreRepository.findAll(pageable);
    }

    // Récupérer un livre par son id
    public Livre getLivreParId(int id) {
        Optional<Livre> livre = livreRepository.findById(id);
        if (livre.isPresent()) {
            return livre.get();
        }
        throw new LivreNotFoundException(id);
    }

    // Rechercher par titre
    public Page<Livre> rechercherParTitre(String titre, Pageable pageable) {
        return livreRepository.findByTitreContainingIgnoreCase(titre, pageable);
    }

    // Rechercher par auteur
    public Page<Livre> rechercherParAuteur(String auteur, Pageable pageable) {
        return livreRepository.findByAuteurContainingIgnoreCase(auteur, pageable);
    }

    // Ajouter un livre
    public Livre ajouterLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    // Modifier un livre
    public Livre modifierLivre(int id, Livre livreModifie) {
        Livre livre = getLivreParId(id);
        livre.setTitre(livreModifie.getTitre());
        livre.setAuteur(livreModifie.getAuteur());
        livre.setAnnee(livreModifie.getAnnee());
        return livreRepository.save(livre);
    }

    // Supprimer un livre
    public void supprimerLivre(int id) {
        if (!livreRepository.existsById(id)) {
            throw new LivreNotFoundException(id);
        }
        livreRepository.deleteById(id);
    }
}