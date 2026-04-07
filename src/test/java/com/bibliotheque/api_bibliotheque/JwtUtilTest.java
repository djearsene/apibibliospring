package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    // Test 1 : genererToken retourne un token non vide
    @Test
    void genererToken_retourneTokenNonVide() {
        String token = jwtUtil.genererToken("admin", "ROLE_ADMIN");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    // Test 2 : extraireUsername retourne le bon username
    @Test
    void extraireUsername_retourneBonUsername() {
        String token = jwtUtil.genererToken("admin", "ROLE_ADMIN");
        String username = jwtUtil.extraireUsername(token);
        assertEquals("admin", username);
    }

    // Test 3 : extraireRole retourne le bon role
    @Test
    void extraireRole_retourneBonRole() {
        String token = jwtUtil.genererToken("admin", "ROLE_ADMIN");
        String role = jwtUtil.extraireRole(token);
        assertEquals("ROLE_ADMIN", role);
    }

    // Test 4 : estValide retourne true pour un token valide
    @Test
    void estValide_retourneTrue_pourTokenValide() {
        String token = jwtUtil.genererToken("admin", "ROLE_ADMIN");
        assertTrue(jwtUtil.estValide(token));
    }

    // Test 5 : estValide retourne false pour un token invalide
    @Test
    void estValide_retourneFalse_pourTokenInvalide() {
        assertFalse(jwtUtil.estValide("token.invalide.ici"));
    }

    // Test 6 : deux tokens différents pour deux utilisateurs différents
    @Test
    void genererToken_produitsTokensDifferents() {
        String token1 = jwtUtil.genererToken("admin", "ROLE_ADMIN");
        String token2 = jwtUtil.genererToken("user", "ROLE_USER");
        assertNotEquals(token1, token2);
    }

    // Test 7 : le role USER est bien extrait
    @Test
    void extraireRole_retourneRoleUser() {
        String token = jwtUtil.genererToken("user", "ROLE_USER");
        String role = jwtUtil.extraireRole(token);
        assertEquals("ROLE_USER", role);
    }
}