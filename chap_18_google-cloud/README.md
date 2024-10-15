## Taco Cloud - Chapter 18:
## *18.3 Building container images*

## *TacoCloud Application Deployment on Google Cloud Kubernetes*

This project demonstrates deploying a Spring Boot application on Google Cloud Kubernetes with MongoDB integration.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Building and Pushing Docker Image](#building-and-pushing-docker-image)
- [Kubernetes Setup](#kubernetes-setup)
- [MongoDB Setup](#mongodb-setup)
- [Spring Boot Application Deployment](#spring-boot-application-deployment)
- [Accessing the Application](#accessing-the-application)

### Prerequisites

Before starting, ensure you have the following tools and services set up:

- A Google Cloud account and project
- Google Kubernetes Engine (GKE) cluster setup
- Google Cloud SDK (`gcloud`)
- Docker Desktop installed and running
- Kubernetes CLI (`kubectl`)
- Maven (to build the Spring Boot application)

### Project Setup

#### 1. Authenticate with Google Cloud
```bash
gcloud auth login
```

#### 2. Set Your Google Cloud Project:
```bash
gcloud config set project <your-project-id>
```
Replace <your-project-id> with the actual ID of your Google Cloud project. 
You can find this in the Google Cloud Console under the "Project Info" section or using:
```bash
gcloud projects list
```

#### 3. Ensure you have a Kubernetes cluster running on Google Cloud. You can create a new GKE cluster using:
```bash
gcloud container clusters create taco-cloud-cluster --zone us-central1-a
```
#### 4. Connect to the GKE cluster: 
```bash
gcloud container clusters get-credentials taco-cloud-cluster --zone us-central1-a --project <your-project-id>
```
### Building and Pushing Docker Image

#### 1. Build the Docker image:
```bash
docker build -t gcr.io/<your-project-id>/tacocloud18gc:latest .
```

#### 2. Push the Docker image to Google Container Registry (GCR):
```bash
docker push gcr.io/<your-project-id>/tacocloud18gc:latest
```

#### 3. Ensure the Docker image is correctly uploaded:
```bash
gcloud container images list
```

### Kubernetes Setup

#### 1. Use kubectl to apply Kubernetes deployment and service configurations.

#### 2. Create the deployment and service for MongoDB:
```bash
kubectl apply -f k8s/mongodb-deployment.yml
```
#### 3. Check the status of MongoDB deployment:
```bash
kubectl get pods
```

### MongoDB Setup

#### Ensure that MongoDB is running in the Kubernetes cluster. You can check the logs with:
```bash
kubectl logs <mongo-pod-name>
```

### Spring Boot Application Deployment

#### 1. Apply the deployment and service for the TacoCloud application:
```bash
kubectl apply -f k8s/deploy.yml
kubectl apply -f k8s/service.yml
```
#### 2. Verify that the application is deployed:
```bash
kubectl get pods
kubectl get services
```
#### 3. If the application is successfully deployed, you would see something like:
```
NAME                 TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)        AGE
taco-cloud-service   LoadBalancer   <cluster-ip>     <external-ip>   80:30000/TCP   <time>
```

### Accessing the Application

To access the application, use the external IP address provided by the kubectl get services command. 
Open it in your browser to access the app.

