package com.medori42.cloudapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur REST fournissant les points d'accès de bienvenue pour l'application cloud.
 * 
 * <p>
 * Ce contrôleur démontre l'intégration des ConfigMaps Kubernetes avec
 * les applications Spring Boot en exposant un point d'accès qui retourne
 * un message de bienvenue configurable.
 * </p>
 * 
 * <p>
 * Les fonctionnalités principales incluent:
 * <ul>
 *   <li>Point d'accès RESTful pour les messages de bienvenue</li>
 *   <li>Configuration externalisée via variables d'environnement</li>
 *   <li>Intégration avec Kubernetes ConfigMap</li>
 * </ul>
 * </p>
 * 
 * @author Medori42
 * @version 2.0.0
 * @since 2024
 * @see org.springframework.web.bind.annotation.RestController
 */
@RestController
public class WelcomeRestController {

    /**
     * Clé utilisée pour le message dans la réponse JSON.
     */
    private static final String RESPONSE_MESSAGE_KEY = "message";

    /**
     * Clé utilisée pour le statut dans la réponse JSON.
     */
    private static final String RESPONSE_STATUS_KEY = "status";

    /**
     * Valeur de statut indiquant une opération réussie.
     */
    private static final String STATUS_SUCCESS_VALUE = "OK";

    /**
     * Message de bienvenue configuré via Kubernetes ConfigMap ou propriétés de l'application.
     * 
     * <p>
     * Cette valeur est injectée depuis la variable d'environnement {@code APP_MESSAGE},
     * avec une valeur par défaut de repli si non configurée.
     * </p>
     */
    @Value("${APP_MESSAGE:Bienvenue depuis la valeur par defaut}")
    private String welcomeMessageContent;

    /**
     * Constructeur par défaut du contrôleur de bienvenue.
     * 
     * <p>
     * Initialise le contrôleur REST pour gérer les requêtes de bienvenue.
     * </p>
     */
    public WelcomeRestController() {
        // Constructeur par défaut
    }

    /**
     * Récupère un message de bienvenue avec le statut de l'application.
     * 
     * <p>
     * Ce point d'accès démontre comment les ConfigMaps Kubernetes peuvent être
     * utilisés pour externaliser la configuration dans les applications Spring Boot
     * conteneurisées. Le message peut être mis à jour en modifiant le ConfigMap
     * sans reconstruire l'application.
     * </p>
     * 
     * <p>
     * Exemple de réponse:
     * <pre>
     * {
     *   "message": "Bienvenue depuis Kubernetes ConfigMap",
     *   "status": "OK"
     * }
     * </pre>
     * </p>
     * 
     * @return une Map contenant le message de bienvenue et le statut de l'application
     */
    @GetMapping("/api/hello")
    public Map<String, String> retrieveWelcomeMessage() {
        return buildResponsePayload();
    }

    /**
     * Construit le payload de réponse contenant le message et le statut.
     * 
     * <p>
     * Cette méthode encapsule la logique de création de la réponse JSON
     * pour assurer une structure cohérente.
     * </p>
     * 
     * @return la Map de réponse avec le message et le statut
     */
    private Map<String, String> buildResponsePayload() {
        Map<String, String> responsePayload = new HashMap<>();
        responsePayload.put(RESPONSE_MESSAGE_KEY, getWelcomeMessageContent());
        responsePayload.put(RESPONSE_STATUS_KEY, STATUS_SUCCESS_VALUE);
        return responsePayload;
    }

    /**
     * Récupère le contenu du message de bienvenue configuré.
     * 
     * <p>
     * Cette méthode fournit un accès encapsulé au message de bienvenue
     * pour permettre des extensions futures.
     * </p>
     * 
     * @return le message de bienvenue configuré
     */
    private String getWelcomeMessageContent() {
        return this.welcomeMessageContent;
    }
}
