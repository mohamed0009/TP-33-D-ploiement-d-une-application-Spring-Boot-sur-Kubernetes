package com.medori42.cloudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de démarrage de l'application Spring Boot Cloud.
 * 
 * <p>
 * Cette application démontre le déploiement d'une application Spring Boot
 * sur Kubernetes avec intégration ConfigMap, configuration de sécurité et
 * points d'accès API RESTful.
 * </p>
 * 
 * <p>
 * L'application utilise Spring Boot 3.2.0 avec Java 17 et fournit une
 * architecture cloud-native prête pour la production.
 * </p>
 * 
 * @author Medori42
 * @version 2.0.0
 * @since 2024
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class CloudApplicationBootstrap {

    /**
     * Constructeur par défaut de la classe de démarrage.
     * 
     * <p>
     * Ce constructeur est utilisé par Spring Boot pour initialiser
     * l'application. Il ne nécessite aucun paramètre.
     * </p>
     */
    public CloudApplicationBootstrap() {
        // Constructeur par défaut requis par Spring Boot
    }

    /**
     * Point d'entrée principal de l'application Spring Boot.
     * 
     * <p>
     * Cette méthode initialise et démarre le contexte d'application Spring,
     * en bootstrappant tous les composants et services configurés.
     * </p>
     * 
     * <p>
     * Le processus de démarrage inclut:
     * <ul>
     *   <li>Chargement de la configuration depuis application.properties</li>
     *   <li>Initialisation des beans Spring</li>
     *   <li>Démarrage du serveur embarqué</li>
     *   <li>Configuration de la sécurité</li>
     * </ul>
     * </p>
     * 
     * @param applicationArguments arguments de ligne de commande passés à l'application
     */
    public static void main(final String[] applicationArguments) {
        initializeApplication(applicationArguments);
    }

    /**
     * Méthode d'initialisation de l'application Spring Boot.
     * 
     * <p>
     * Cette méthode encapsule la logique de démarrage de l'application
     * et peut être étendue pour ajouter des configurations supplémentaires.
     * </p>
     * 
     * @param runtimeArguments les arguments de démarrage de l'application
     */
    private static void initializeApplication(final String[] runtimeArguments) {
        SpringApplication.run(CloudApplicationBootstrap.class, runtimeArguments);
    }
}
