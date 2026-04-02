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
        when(livreRepository.existsById(1)).thenReturn(true);
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
}