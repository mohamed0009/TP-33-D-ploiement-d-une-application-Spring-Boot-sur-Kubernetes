package com.medori42.cloudapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration de sécurité web pour l'application cloud.
 * 
 * <p>
 * Cette classe de configuration établit Spring Security avec le support CORS
 * et permet l'accès public aux points d'accès API. Elle est conçue pour
 * les environnements Kubernetes en démonstration.
 * </p>
 * 
 * <p>
 * Fonctionnalités de sécurité configurées:
 * <ul>
 *   <li>Configuration CORS personnalisée</li>
 *   <li>Désactivation CSRF pour APIs REST stateless</li>
 *   <li>Autorisation des requêtes vers /api/**</li>
 *   <li>Authentification requise pour les autres endpoints</li>
 * </ul>
 * </p>
 * 
 * @author Medori42
 * @version 2.0.0
 * @since 2024
 * @see org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    /**
     * Pattern pour les endpoints API publics.
     */
    private static final String PUBLIC_API_PATTERN = "/api/**";

    /**
     * Pattern pour toutes les routes.
     */
    private static final String ALL_ROUTES_PATTERN = "/**";

    /**
     * Wildcard pour autoriser toutes les origines.
     */
    private static final String ALLOW_ALL_ORIGINS = "*";

    /**
     * Wildcard pour autoriser tous les headers.
     */
    private static final String ALLOW_ALL_HEADERS = "*";

    /**
     * Liste des méthodes HTTP autorisées.
     */
    private static final List<String> ALLOWED_HTTP_METHODS = Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
    );

    /**
     * Constructeur par défaut de la configuration de sécurité.
     * 
     * <p>
     * Initialise la configuration de sécurité web avec les paramètres par défaut.
     * </p>
     */
    public WebSecurityConfiguration() {
        // Constructeur par défaut
    }

    /**
     * Configure la chaîne de filtres de sécurité pour les requêtes HTTP.
     * 
     * <p>
     * Cette configuration:
     * <ul>
     *   <li>Active CORS avec configuration personnalisée</li>
     *   <li>Désactive la protection CSRF (adapté pour APIs REST stateless)</li>
     *   <li>Autorise toutes les requêtes vers /api/** endpoints</li>
     *   <li>Requiert l'authentification pour toutes les autres requêtes</li>
     * </ul>
     * </p>
     * 
     * @param httpSecurityBuilder le {@link HttpSecurity} à configurer
     * @return la {@link SecurityFilterChain} configurée
     * @throws Exception si une erreur survient pendant la configuration
     */
    @Bean
    public SecurityFilterChain configureSecurityFilterChain(
            final HttpSecurity httpSecurityBuilder) throws Exception {
        
        configureCorsSecurity(httpSecurityBuilder);
        configureCsrfProtection(httpSecurityBuilder);
        configureRequestAuthorization(httpSecurityBuilder);

        return httpSecurityBuilder.build();
    }

    /**
     * Configure la sécurité CORS sur le builder HttpSecurity.
     * 
     * @param httpSecurityBuilder le builder de sécurité HTTP
     * @throws Exception si une erreur survient
     */
    private void configureCorsSecurity(
            final HttpSecurity httpSecurityBuilder) throws Exception {
        
        httpSecurityBuilder.cors(corsCustomizer -> 
            corsCustomizer.configurationSource(createCorsConfigurationSource())
        );
    }

    /**
     * Configure la protection CSRF sur le builder HttpSecurity.
     * 
     * @param httpSecurityBuilder le builder de sécurité HTTP
     * @throws Exception si une erreur survient
     */
    private void configureCsrfProtection(
            final HttpSecurity httpSecurityBuilder) throws Exception {
        
        httpSecurityBuilder.csrf(AbstractHttpConfigurer::disable);
    }

    /**
     * Configure les autorisations de requêtes sur le builder HttpSecurity.
     * 
     * @param httpSecurityBuilder le builder de sécurité HTTP
     * @throws Exception si une erreur survient
     */
    private void configureRequestAuthorization(
            final HttpSecurity httpSecurityBuilder) throws Exception {
        
        httpSecurityBuilder.authorizeHttpRequests(authorizationConfigurer ->
            authorizationConfigurer
                .requestMatchers(PUBLIC_API_PATTERN).permitAll()
                .anyRequest().authenticated()
        );
    }

    /**
     * Configure les paramètres de Cross-Origin Resource Sharing (CORS).
     * 
     * <p>
     * Cette configuration autorise:
     * <ul>
     *   <li>Toutes les origines à accéder à l'API</li>
     *   <li>Les méthodes HTTP courantes (GET, POST, PUT, DELETE, OPTIONS, PATCH)</li>
     *   <li>Tous les headers dans les requêtes</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <strong>Note:</strong> Dans les environnements de production, considérez
     * restreindre les origines autorisées à des domaines spécifiques pour une
     * sécurité renforcée.
     * </p>
     * 
     * @return le {@link CorsConfigurationSource} configuré
     */
    @Bean
    public CorsConfigurationSource createCorsConfigurationSource() {
        CorsConfiguration corsConfig = buildCorsConfiguration();
        
        UrlBasedCorsConfigurationSource corsConfigSource = 
            new UrlBasedCorsConfigurationSource();
        corsConfigSource.registerCorsConfiguration(ALL_ROUTES_PATTERN, corsConfig);
        
        return corsConfigSource;
    }

    /**
     * Construit la configuration CORS avec les paramètres définis.
     * 
     * @return la configuration CORS
     */
    private CorsConfiguration buildCorsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList(ALLOW_ALL_ORIGINS));
        corsConfig.setAllowedMethods(ALLOWED_HTTP_METHODS);
        corsConfig.setAllowedHeaders(Arrays.asList(ALLOW_ALL_HEADERS));
        return corsConfig;
    }
}
