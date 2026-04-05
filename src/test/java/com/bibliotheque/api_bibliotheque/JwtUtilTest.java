package com.bibliotheque.api_bibliotheque;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

  private final JwtUtil jwtUtil = new JwtUtil();

  // Test 1 : genererToken retourne un token non vide
  @Test
  void genererToken_retourneTokenNonVide() {
    String token = jwtUtil.genererToken("admin");
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  // Test 2 : extraireUsername retourne le bon username
  @Test
  void extraireUsername_retourneBonUsername() {
    String token = jwtUtil.genererToken("admin");
    String username = jwtUtil.extraireUsername(token);
    assertEquals("admin", username);
  }

  // Test 3 : estValide retourne true pour un token valide
  @Test
  void estValide_retourneTrue_pourTokenValide() {
    String token = jwtUtil.genererToken("admin");
    assertTrue(jwtUtil.estValide(token));
  }

  // Test 4 : estValide retourne false pour un token invalide
  @Test
  void estValide_retourneFalse_pourTokenInvalide() {
    assertFalse(jwtUtil.estValide("token.invalide.ici"));
  }

  // Test 5 : deux tokens différents pour deux utilisateurs différents
  @Test
  void genererToken_produitsTokensDifferents() {
    String token1 = jwtUtil.genererToken("admin");
    String token2 = jwtUtil.genererToken("user");
    assertNotEquals(token1, token2);
  }
}