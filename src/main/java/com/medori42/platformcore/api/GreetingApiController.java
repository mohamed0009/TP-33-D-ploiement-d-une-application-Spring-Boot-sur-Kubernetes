package com.medori42.platformcore.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API controller for greeting endpoints.
 * <p>
 * Demonstrates externalized configuration and Kubernetes ConfigMap integration.
 * </p>
 * @author Medori42
 * @version 2.0.0
 * @since 2025
 */
@RestController
public class GreetingApiController {

    /**
     * Key for the greeting message in the response.
     */
    private static final String GREETING_KEY = "greeting";

    /**
     * Key for the status in the response.
     */
    private static final String STATUS_KEY = "status";

    /**
     * Value indicating a successful operation.
     */
    private static final String STATUS_OK = "OK";

    /**
     * Greeting message injected from environment or default value.
     */
    @Value("${APP_GREETING:Welcome from the default value}")
    private String greetingMessage;

    /**
     * Default constructor for GreetingApiController.
     */
    public GreetingApiController() {
        // Default constructor
    }

    /**
     * Retrieves a greeting message and status.
     * @return Map containing greeting and status
     */
    @GetMapping("/api/greet")
    public Map<String, String> getGreeting() {
        return buildGreetingResponse();
    }

    /**
     * Builds the response payload for the greeting endpoint.
     * @return Map with greeting and status
     */
    private Map<String, String> buildGreetingResponse() {
        Map<String, String> response = new HashMap<>();
        response.put(GREETING_KEY, fetchGreetingMessage());
        response.put(STATUS_KEY, STATUS_OK);
        return response;
    }

    /**
     * Fetches the configured greeting message.
     * @return The greeting message
     */
    private String fetchGreetingMessage() {
        return this.greetingMessage;
    }
}
