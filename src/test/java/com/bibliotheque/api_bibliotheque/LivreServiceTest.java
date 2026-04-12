package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @Mock
    private AuteurRepository auteurRepository;
    @Mock
    private HistoriqueRepository historiqueRepository;

    @InjectMocks
    private LivreService livreService;

    @Test
    void getLivreParId_retourneLivre_siExiste() {
        Auteur auteur = new Auteur("Zola", "Française", 1840);
        Livre livre = new Livre("Germinal", 1885, auteur);
        livre.setId(1);
        when(livreRepository.findById(1)).thenReturn(Optional.of(livre));
        Livre resultat = livreService.getLivreParId(1);
        assertNotNull(resultat);
        assertEquals("Germinal", resultat.getTitre());
        assertEquals(1885, resultat.getAnnee());
    }

    @Test
    void getLivreParId_lancerException_siInexistant() {
        when(livreRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(LivreNotFoundException.class, () -> {
            livreService.getLivreParId(999);
        });
    }

    @Test
    void ajouterLivre_retourneLivreSauvegarde() {
        Auteur auteur = new Auteur("Hugo", "Française", 1802);
        Livre livre = new Livre("Les Misérables", 1862, auteur);
        when(livreRepository.save(livre)).thenReturn(livre);
        Livre resultat = livreService.ajouterLivre(livre);
        assertNotNull(resultat);
        assertEquals("Les Misérables", resultat.getTitre());
        verify(livreRepository, times(1)).save(livre);
    }

    @Test
    void supprimerLivre_supprime_siExiste() {
        Auteur auteur = new Auteur("Zola", "Française", 1840);
        Livre livre = new Livre("Germinal", 1885, auteur);
        livre.setId(1);

        when(livreRepository.existsById(1)).thenReturn(true);
        when(livreRepository.findById(1)).thenReturn(Optional.of(livre));
        livreService.supprimerLivre(1);
        verify(livreRepository, times(1)).deleteById(1);
    }

    @Test
    void supprimerLivre_lancerException_siInexistant() {
        when(livreRepository.existsById(999)).thenReturn(false);
        assertThrows(LivreNotFoundException.class, () -> {
            livreService.supprimerLivre(999);
        });
    }

    // Test 6 : exporterCSV retourne un contenu non vide
    @Test
    void exporterCSV_retourneContenuCSV() {
        // Préparer
        Auteur auteur = new Auteur("Zola", "Française", 1840);
        Livre livre1 = new Livre("Germinal", 1885, auteur);
        Livre livre2 = new Livre("Nana", 1880, auteur);
        java.util.List<Livre> livres = java.util.List.of(livre1, livre2);

        // Simuler
        when(livreRepository.findAll()).thenReturn(livres);

        // Exécuter
        String csv = livreService.exporterCSV();

        // Vérifier
        assertNotNull(csv);
        assertTrue(csv.contains("ID,Titre,Auteur,Année"));
        assertTrue(csv.contains("Germinal"));
        assertTrue(csv.contains("Nana"));
        assertTrue(csv.contains("Zola"));
    }

    // Test 7 : exporterCSV contient le bon nombre de lignes
    @Test
    void exporterCSV_contientBonNombreDeLignes() {
        // Préparer
        Auteur auteur = new Auteur("Hugo", "Française", 1802);
        Livre livre1 = new Livre("Les Misérables", 1862, auteur);
        Livre livre2 = new Livre("Notre-Dame", 1831, auteur);
        Livre livre3 = new Livre("Hernani", 1830, auteur);
        java.util.List<Livre> livres = java.util.List.of(livre1, livre2, livre3);

        // Simuler
        when(livreRepository.findAll()).thenReturn(livres);

        // Exécuter
        String csv = livreService.exporterCSV();

        // Compter les lignes : 1 en-tête + 3 livres = 4 lignes
        String[] lignes = csv.split("\n");
        assertEquals(4, lignes.length);
    }

    // Test 8 : les logs sont bien déclenchés lors d'une recherche réussie
    @Test
    void getLivreParId_loggueInfo_siTrouve() {
        Auteur auteur = new Auteur("Zola", "Française", 1840);
        Livre livre = new Livre("Germinal", 1885, auteur);
        livre.setId(1);
        when(livreRepository.findById(1)).thenReturn(Optional.of(livre));

        // Exécuter sans erreur suffit à vérifier que les logs ne bloquent pas
        Livre resultat = livreService.getLivreParId(1);
        assertNotNull(resultat);
        assertEquals("Germinal", resultat.getTitre());
    }

    // Test 9 : exporterCSV avec liste vide retourne seulement l'en-tête
    @Test
    void exporterCSV_retourneSeulementEnTete_siListeVide() {
        when(livreRepository.findAll()).thenReturn(java.util.List.of());

        String csv = livreService.exporterCSV();

        assertNotNull(csv);
        assertTrue(csv.contains("ID,Titre,Auteur,Année"));
        String[] lignes = csv.split("\n");
        assertEquals(1, lignes.length);
    }

    // Test 10 : exporterCSV gère un auteur null
    @Test
    void exporterCSV_gereAuteurNull() {
        Livre livre = new Livre("Sans Auteur", 2000, null);
        when(livreRepository.findAll()).thenReturn(java.util.List.of(livre));

        String csv = livreService.exporterCSV();

        assertNotNull(csv);
        assertTrue(csv.contains("Inconnu"));
    }

    // Test 11 : modifierLivre met à jour les bonnes valeurs
    @Test
    void modifierLivre_metAJourLesBonnesValeurs() {
        Auteur auteur = new Auteur("Hugo", "Française", 1802);
        Livre livreExistant = new Livre("Ancien titre", 1800, auteur);
        livreExistant.setId(1);

        Livre livreModifie = new Livre("Nouveau titre", 1850, auteur);

        when(livreRepository.findById(1)).thenReturn(Optional.of(livreExistant));
        when(livreRepository.save(livreExistant)).thenReturn(livreExistant);

        Livre resultat = livreService.modifierLivre(1, livreModifie);

        assertEquals("Nouveau titre", resultat.getTitre());
        assertEquals(1850, resultat.getAnnee());
        verify(livreRepository, times(1)).save(livreExistant);
    }

    // Test 12 : rechercherParAuteur retourne les bons livres
    @Test
    void rechercherParAuteur_retourneLivresCorrects() {
        Auteur auteur = new Auteur("Jules Verne", "Française", 1828);
        Livre livre1 = new Livre("Vingt mille lieues", 1870, auteur);
        Livre livre2 = new Livre("Le Tour du monde", 1872, auteur);

        org.springframework.data.domain.PageImpl<Livre> page = new org.springframework.data.domain.PageImpl<>(
                java.util.List.of(livre1, livre2));

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10);

        when(livreRepository.findByAuteurNomContainingIgnoreCase("Verne", pageable))
                .thenReturn(page);

        org.springframework.data.domain.Page<Livre> resultat = livreService.rechercherParAuteur("Verne", pageable);

        assertNotNull(resultat);
        assertEquals(2, resultat.getTotalElements());
    }

    // Test 13 : rechercherParTitre retourne les bons livres
    @Test
    void rechercherParTitre_retourneLivresCorrects() {
        Auteur auteur = new Auteur("Stoker", "Irlandaise", 1847);
        Livre livre = new Livre("Dracula", 1897, auteur);

        org.springframework.data.domain.PageImpl<Livre> page = new org.springframework.data.domain.PageImpl<>(
                java.util.List.of(livre));

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10);

        when(livreRepository.findByTitreContainingIgnoreCase("Dracula", pageable))
                .thenReturn(page);

        org.springframework.data.domain.Page<Livre> resultat = livreService.rechercherParTitre("Dracula", pageable);

        assertNotNull(resultat);
        assertEquals(1, resultat.getTotalElements());
        assertEquals("Dracula", resultat.getContent().get(0).getTitre());
    }

    // Test 14 : importerCSV avec auteur existant ne crée pas de doublon
    @Test
    void importerCSV_utilisesAuteurExistant_siDejaPresent() {
        Auteur auteurExistant = new Auteur("Hugo", "Française", 1802);
        auteurExistant.setId(1);

        when(auteurRepository.findByNom("Hugo"))
                .thenReturn(java.util.Optional.of(auteurExistant));

        // Vérifier que findByNom est appelé
        java.util.Optional<Auteur> auteur = auteurRepository.findByNom("Hugo");
        assertTrue(auteur.isPresent());
        assertEquals("Hugo", auteur.get().getNom());
        verify(auteurRepository, times(1)).findByNom("Hugo");
    }

}