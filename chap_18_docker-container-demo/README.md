## Taco Cloud - Chapter 18:
## *18.3.1 Deploying to Kubernetes*
This project demonstrates how to deploy a Spring Boot application (`chap_18_docker-container-demo`) integrated with MongoDB in a Kubernetes cluster using Docker. The project involves building a Docker image for the application, setting up a MongoDB database, and deploying both to a Kubernetes cluster for testing.

### Technologies Used

- **MongoDB**
- **Docker**
- **Kubernetes (Kind)**
- **kubectl** (Kubernetes CLI)
- **Docker Compose** (for initial MongoDB setup)

## Prerequisites

Ensure you have the following installed and configured on your system:

- [Docker](https://www.docker.com/products/docker-desktop)
- [Kind](https://kind.sigs.k8s.io/) (Kubernetes in Docker)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)

## Project Structure

- **Dockerfile**: Contains instructions to build the Docker image for the Spring Boot application.
- **Kubernetes manifests**: YAML files (`deploy.yml` and `service.yml`) for deploying the application and MongoDB in Kubernetes.
- **MongoDB Deployment**: MongoDB is deployed using a Kubernetes `StatefulSet` with a persistent volume.

## Getting Started

### 1. Building the Docker Image for the Application

First, make sure you have built the Spring Boot JAR file:

```bash
mvn clean package
```

Now, build the Docker image for the application:
```bash
docker build -t tacocloud/tacocloud:latest .
```

If you are using Kind, load the image into the Kind cluster:
```bash
kind load docker-image tacocloud/tacocloud:latest --name taco-cluster
```
### 2. Deploying MongoDB
Before deploying the application, you need to ensure MongoDB is up and running. Use the following command to run MongoDB locally with Docker:
```bash
docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest
```

Alternatively, you can deploy MongoDB in Kubernetes using a mongodb-deployment.yml:
```bash
kubectl apply -f k8s/mongodb-deployment.yml
```
### 3. Deploying the Application to Kubernetes

Once MongoDB is running, you can deploy the Spring Boot application using Kubernetes:

#### Step 1: Apply Deployment
```bash
kubectl apply -f k8s/deploy.yml
```
#### Step 2: Apply Service

```bash
kubectl apply -f k8s/service.yml
```

### 4. Accessing the Application

Option 1: 
- Using kubectl port-forward
To access the application locally, you can use kubectl port-forward:
```bash
kubectl port-forward svc/taco-cloud-service 8080:80
```

Now you can access the application on:
```bash
http://localhost:8080
```

Option 2: 
- Using NodePort
Alternatively, the application is exposed through NodePort at port 30000:
```bash
http://<Node-IP>:30000
```
Where <Node-IP> is the IP address of the Kubernetes node. You can find it by inspecting the Kind cluster node:
```bash
docker inspect taco-cluster-control-plane | findstr "IPAddress"
```

Then access the application using the node IP:
```bash
http://<IPAddress>:30000
```

### 5. Stopping the Application

If you want to stop the application, you can scale down the deployment or delete the resources:
```bash
kubectl scale deployment taco-cloud-deploy --replicas=0
```

Alternatively, to fully remove the deployment:
```bash
kubectl delete deployment taco-cloud-deploy
```
```bash
kubectl delete service taco-cloud-service
```
#### Troubleshooting
```bash
kind create cluster --name taco-cluster
kubectl cluster-info
kubectl get pods
kubectl get services
kubectl logs <pod-name>
kubectl exec -it <pod-name> -- curl mongo:27017
```

