#!/bin/bash

# Cleanup script for Spring Boot Kubernetes Demo
# Author: KiAA Khalid

set -e

echo "🧹 Starting cleanup process..."

# Colors for output
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Step 1: Delete service
echo -e "${BLUE}Step 1: Deleting service...${NC}"
kubectl delete -f k8s-service.yaml --ignore-not-found=true

# Step 2: Delete deployment
echo -e "${BLUE}Step 2: Deleting deployment...${NC}"
kubectl delete -f k8s-deployment.yaml --ignore-not-found=true

# Step 3: Delete ConfigMap
echo -e "${BLUE}Step 3: Deleting ConfigMap...${NC}"
kubectl delete -f k8s-configmap.yaml --ignore-not-found=true

# Step 4: Delete namespace (optional - commented out by default)
# echo -e "${BLUE}Step 4: Deleting namespace...${NC}"
# kubectl delete -f k8s-namespace.yaml --ignore-not-found=true

echo -e "${RED}✅ Cleanup completed!${NC}"
echo ""
echo "Remaining resources in k8s-demo namespace:"
kubectl get all -n k8s-demo 2>/dev/null || echo "Namespace is empty or deleted"
