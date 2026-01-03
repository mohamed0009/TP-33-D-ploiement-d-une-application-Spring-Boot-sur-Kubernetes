
# Spring Boot Kubernetes Demo

Spring Boot application demonstrating Kubernetes deployment with ConfigMap integration, security configuration, and RESTful APIs.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Local Development](#local-development)
- [Docker Build](#docker-build)
- [Kubernetes Deployment](#kubernetes-deployment)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Testing](#testing)
- [Author](#author)

## 🎯 Overview

This project demonstrates a production-ready Spring Boot application designed for Kubernetes deployment. It showcases best practices for containerization, configuration management, and cloud-native application development.

### Key Highlights

- **Cloud-Native Architecture**: Built specifically for Kubernetes environments
- **ConfigMap Integration**: Externalized configuration using Kubernetes ConfigMaps
- **Security**: Spring Security with CORS configuration
- **RESTful APIs**: Clean and well-documented REST endpoints
- **Health Checks**: Kubernetes liveness and readiness probes
- **Resource Management**: Proper CPU and memory limits

## ✨ Features

- ✅ Spring Boot 3.2.0 with Java 17
- ✅ RESTful API with greeting endpoint
- ✅ Kubernetes ConfigMap integration for dynamic configuration
- ✅ Spring Security with CORS support
- ✅ MySQL database integration with JPA/Hibernate
- ✅ Docker containerization
- ✅ Kubernetes deployment manifests
- ✅ Health check endpoints (liveness & readiness probes)
- ✅ Resource limits and requests configuration
- ✅ Comprehensive JavaDoc documentation

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Kubernetes Cluster                    │
│                                                          │
│  ┌────────────────────────────────────────────────┐    │
│  │           Namespace: k8s-demo                  │    │
│  │                                                 │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │   ConfigMap: springboot-k8s-config   │     │    │
│  │  │   - app.message: "Hello..."          │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  │                    ↓                            │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Deployment: springboot-k8s          │     │    │
│  │  │  - Replicas: 2                       │     │    │
│  │  │  - Image: springboot-k8s-demo:1.0.0  │     │    │
│  │  │  - Port: 8081                        │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  │                    ↓                            │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Service: springboot-k8s-service     │     │    │
│  │  │  - Type: NodePort                    │     │    │
│  │  │  - Port: 8081                        │     │    │
│  │  │  - NodePort: 30080                   │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  └────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
```

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.8+** for building the project
- **Docker** for containerization
- **Kubernetes** (Minikube, Docker Desktop, or any K8s cluster)
- **kubectl** for Kubernetes management
- **MySQL 8.0+** (for local development)

## 🚀 Local Development


### 1. Project Setup

Download or copy the project files to your local machine.

### 2. Configure Database

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/k8s_demo_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

### 5. Test the API

```bash
curl http://localhost:8081/api/hello
```

Expected response:
```json
{
  "message": "Hello from default value",
  "status": "OK"
}
```

## 🐳 Docker Build

### Build Docker Image

```bash
# Build the JAR file first
mvn clean package

# Build Docker image
docker build -t springboot-k8s-demo:1.0.0 .
```

### Run Docker Container

```bash
docker run -p 8081:8081 \
  -e APP_MESSAGE="Hello from Docker" \
  springboot-k8s-demo:1.0.0
```

### Verify Container

```bash
curl http://localhost:8081/api/hello
```

## 🐳 Docker Compose (Local Development)

For local development with MySQL database:

### Start Services

```bash
docker-compose up -d
```

This will start:
- MySQL database on port 3306
- Spring Boot application on port 8081

### Stop Services

```bash
docker-compose down
```

### View Logs

```bash
docker-compose logs -f springboot-app
```

## ☸️ Kubernetes Deployment

### 1. Create Namespace

```bash
kubectl create namespace k8s-demo
```

### 2. Apply ConfigMap

```bash
kubectl apply -f k8s-configmap.yaml
```

### 3. Deploy Application

```bash
kubectl apply -f k8s-deployment.yaml
```

### 4. Create Service

```bash
kubectl apply -f k8s-service.yaml
```

### 5. Verify Deployment

```bash
# Check pods
kubectl get pods -n k8s-demo

# Check service
kubectl get svc -n k8s-demo

# Check deployment
kubectl get deployment -n k8s-demo
```

### 6. Access the Application

If using Minikube:
```bash
minikube service springboot-k8s-service -n k8s-demo
```

Or access directly via NodePort:
```bash
curl http://<node-ip>:30080/api/hello
```

### 7. View Logs

```bash
kubectl logs -f deployment/springboot-k8s-deployment -n k8s-demo
```

## 📚 API Documentation

### Greeting Endpoint

**Endpoint:** `GET /api/hello`

**Description:** Returns a greeting message configured via ConfigMap

**Response:**
```json
{
  "message": "Hello from Kubernetes ConfigMap - Spring Boot Demo by Bilal Khan",
  "status": "OK"
}
```

**Status Codes:**
- `200 OK` - Success
- `500 Internal Server Error` - Server error

## ⚙️ Configuration

### Application Properties

| Property | Description | Default Value |
|----------|-------------|---------------|
| `spring.application.name` | Application name | `springboot-k8s-demo` |
| `server.port` | Server port | `8081` |
| `spring.datasource.url` | Database URL | `jdbc:mysql://localhost:3306/k8s_demo_db` |
| `APP_MESSAGE` | Greeting message | `Hello from default value` |

### Kubernetes ConfigMap

The application uses ConfigMap for externalized configuration:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: springboot-k8s-config
  namespace: k8s-demo
data:
  app.message: "Hello from Kubernetes ConfigMap"
```

To update the message without redeploying:

```bash
kubectl edit configmap springboot-k8s-config -n k8s-demo
# Restart pods to pick up changes
kubectl rollout restart deployment/springboot-k8s-deployment -n k8s-demo
```

### Resource Limits

The deployment includes resource management:

```yaml
resources:
  requests:
    memory: "256Mi"
    cpu: "250m"
  limits:
    memory: "512Mi"
    cpu: "500m"
```

## 🧪 Testing

### Run Unit Tests

```bash
mvn test
```

### Run Integration Tests

```bash
mvn verify
```

### Test Coverage

```bash
mvn clean test jacoco:report
```

View coverage report at: `target/site/jacoco/index.html`

## 🔧 Troubleshooting

### Common Issues

**Issue:** Pods not starting
```bash
kubectl describe pod <pod-name> -n k8s-demo
kubectl logs <pod-name> -n k8s-demo
```

**Issue:** ConfigMap not loading
```bash
kubectl get configmap -n k8s-demo
kubectl describe configmap springboot-k8s-config -n k8s-demo
```

**Issue:** Service not accessible
```bash
kubectl get svc -n k8s-demo
kubectl describe svc springboot-k8s-service -n k8s-demo
```

## 📝 Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/kiaa/k8sdemo/
│   │   │   ├── K8sDemoApplication.java
│   │   │   ├── controller/
│   │   │   │   └── GreetingController.java
│   │   │   └── configuration/
│   │   │       └── SecurityConfig.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/kiaa/k8sdemo/
│           └── K8sDemoApplicationTests.java
├── k8s-configmap.yaml
├── k8s-deployment.yaml
├── k8s-service.yaml
├── Dockerfile
├── pom.xml
└── README.md
```


## 👨‍💻 Author

**Bilal Khan**
Email: bilal.khan@example.com

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- Kubernetes Community for comprehensive documentation
- All contributors who help improve this project

---

**Note:** This is a demonstration project for educational purposes. For production use, ensure proper security configurations, monitoring, and logging are in place.
"# TP-33-D-ploiement-d-une-application-Spring-Boot-sur-Kubernetes" 
