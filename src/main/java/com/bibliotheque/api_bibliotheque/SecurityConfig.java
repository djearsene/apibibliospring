package com.bibliotheque.api_bibliotheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // Bean pour le hachage des mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        // GET est public
                        .requestMatchers(HttpMethod.GET, "/livres/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auteurs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/statistiques/**").permitAll()
                        // Historique réservé à ROLE_ADMIN
                        .requestMatchers("/historique/**").hasAuthority("ROLE_ADMIN")
                        // Commentaires : lecture publique, écriture authentifiée
.requestMatchers(HttpMethod.GET, "/commentaires/**").permitAll()
.requestMatchers(HttpMethod.POST, "/commentaires/**").authenticated()
.requestMatchers(HttpMethod.DELETE, "/commentaires/**").hasAuthority("ROLE_ADMIN")
                        // Import et export nécessitent ROLE_ADMIN
                        .requestMatchers("/livres/import/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/livres/export/**").hasAuthority("ROLE_ADMIN")
                        // POST, PUT, DELETE nécessitent ROLE_ADMIN
                        .requestMatchers(HttpMethod.POST, "/livres/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/livres/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/livres/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ROLE_ADMIN")
                        // Tout le reste nécessite une authentification
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}