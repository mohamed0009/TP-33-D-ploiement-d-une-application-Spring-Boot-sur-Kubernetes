#!/bin/bash

# Deployment script for Spring Boot Kubernetes Demo
# Author: KiAA Khalid

set -e

echo "🚀 Starting deployment process..."

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Step 1: Build the application
echo -e "${BLUE}Step 1: Building application...${NC}"
mvn clean package -DskipTests

# Step 2: Build Docker image
echo -e "${BLUE}Step 2: Building Docker image...${NC}"
docker build -t springboot-k8s-demo:1.0.0 .

# Step 3: Create namespace
echo -e "${BLUE}Step 3: Creating Kubernetes namespace...${NC}"
kubectl apply -f k8s-namespace.yaml

# Step 4: Apply ConfigMap
echo -e "${BLUE}Step 4: Applying ConfigMap...${NC}"
kubectl apply -f k8s-configmap.yaml

# Step 5: Deploy application
echo -e "${BLUE}Step 5: Deploying application...${NC}"
kubectl apply -f k8s-deployment.yaml

# Step 6: Create service
echo -e "${BLUE}Step 6: Creating service...${NC}"
kubectl apply -f k8s-service.yaml

# Step 7: Wait for deployment
echo -e "${BLUE}Step 7: Waiting for deployment to be ready...${NC}"
kubectl wait --for=condition=available --timeout=120s deployment/springboot-k8s-deployment -n k8s-demo

# Step 8: Display status
echo -e "${GREEN}✅ Deployment completed successfully!${NC}"
echo ""
echo "Deployment status:"
kubectl get all -n k8s-demo

echo ""
echo "Access the application at: http://<node-ip>:30080/api/hello"
