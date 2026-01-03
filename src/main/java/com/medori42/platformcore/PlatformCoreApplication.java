package com.medori42.platformcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Platform Core Application.
 * <p>
 * This application demonstrates a modern cloud-native architecture with Spring Boot and Kubernetes integration.
 * </p>
 * @author Medori42
 * @version 2.0.0
 * @since 2025
 */
@SpringBootApplication
public class PlatformCoreApplication {

    /**
     * Application entry point.
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        PlatformCoreApplication app = new PlatformCoreApplication();
        app.start(args);
    }

    /**
     * Starts the Spring Boot application using instance method.
     * @param startupArgs Arguments for application startup
     */
    private void start(final String[] startupArgs) {
        SpringApplication.run(PlatformCoreApplication.class, startupArgs);
    }

    /**
     * Default constructor for PlatformCoreApplication.
     */
    public PlatformCoreApplication() {
        // Default constructor
    }
}
