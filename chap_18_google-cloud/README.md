## Taco Cloud - Chapter 18:
## *18.3 Building container images*

## *TacoCloud Application Deployment on Google Cloud Kubernetes*

This project demonstrates deploying a Spring Boot application on Google Cloud Kubernetes with MongoDB and Redis integration.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Building and Pushing Docker Image](#building-and-pushing-docker-image)
- [Kubernetes Setup](#kubernetes-setup)
- [Accessing the Application](#accessing-the-application)

### Prerequisites

Before starting, ensure you have the following tools and services set up:

- Google Cloud account and project
- Google Kubernetes Engine (GKE) cluster
- Google Cloud SDK (`gcloud`)
- Docker Desktop installed and running
- Kubernetes CLI (`kubectl`)

### Project Setup

#### 1. Authenticate with Google Cloud
```bash
gcloud auth login
```

#### 2. Set Your Google Cloud Project:
Replace <your-project-id> with the actual ID of your Google Cloud project.
```bash
gcloud config set project <your-project-id>
```

You can find this in the Google Cloud Console under the "Project Info" section or using:
```bash
gcloud projects list
```

#### 3. Create a Kubernetes Cluster:
Ensure you have a Kubernetes cluster running on Google Cloud. You can create a new GKE cluster with:
```bash
gcloud container clusters create taco-cloud-cluster --zone us-central1-a
```
#### 4. Connect to the GKE cluster: 
Link your Kubernetes CLI to the GKE cluster:
```bash
gcloud container clusters get-credentials taco-cloud-cluster --zone us-central1-a --project <your-project-id>
```
### Building and Pushing Docker Image

#### 1. Build the Docker image:
Replace <your-project-id> and <your-cluster-name> with your project and cluster names:
```bash
docker build -t gcr.io/<your-project-id>/<your-cluster-name>:latest .
```

#### 2. Push the Docker image to Google Container Registry (GCR):
```bash
docker push gcr.io/<your-project-id>/<your-cluster-name>:latest
```

#### 3. Verify the Docker Image in GCR:
```bash
gcloud container images list
```

### Kubernetes Setup

#### 1. Deploy MongoDB:
```bash
kubectl apply -f k8s/mongodb-deployment.yml
```
#### 2. Deploy Redis:
```bash
kubectl apply -f k8s/redis-deployment.yml
```
#### 3. Check the status of MongoDB and Redis deployments:
```bash
kubectl get pods
```

#### 4. Deploy the TacoCloud application:
Once MongoDB and Redis pods are running, apply the application deployment configuration:
```bash
kubectl apply -f k8s/deployment.yml
```

#### 5. Verify All Pods:
Check the status of all pods to ensure they’re running:
```bash
kubectl get pods
```

#### 6. Check the Service:
```bash
kubectl get svc taco-cloud-service
```
If the application has been successfully deployed, you will see something like:
```
NAME                 TYPE           CLUSTER-IP       EXTERNAL-IP     PORT(S)        AGE
taco-cloud-service   LoadBalancer   <cluster-ip>     <external-ip>   80:30000/TCP   <time>
```

### Accessing the Application

To access the application, use the external IP address provided by the kubectl get services command. 
Open it in your browser to access the app.
```
http://<external-ip>
```
