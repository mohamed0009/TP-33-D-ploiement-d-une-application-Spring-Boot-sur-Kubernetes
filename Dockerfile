# Utilisation de Eclipse Temurin JDK 17 sur Alpine Linux pour une taille d'image minimale
FROM eclipse-temurin:17-jdk-alpine

# Definition des labels de metadonnees
LABEL maintainer="Medori42 <medori42@github.com>"
LABEL description="Application Cloud Microservice Platform - Spring Boot sur Kubernetes"
LABEL version="2.0.0"
LABEL author="Medori42"

# Definition du repertoire de travail
WORKDIR /application

# Copie du fichier JAR compile depuis le repertoire target
COPY target/cloud-microservice-platform-2.0.0-RELEASE.jar application.jar

# Exposition du port de l'application
EXPOSE 8081

# Demarrage de l'application
ENTRYPOINT ["java","-jar","application.jar"]