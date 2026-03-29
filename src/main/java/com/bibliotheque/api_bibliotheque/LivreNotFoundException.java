package com.bibliotheque.api_bibliotheque;

public class LivreNotFoundException extends RuntimeException {

    public LivreNotFoundException(int id) {
        super("Livre non trouvé avec l'id : " + id);
    }
}