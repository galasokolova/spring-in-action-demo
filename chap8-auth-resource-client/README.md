## Taco Cloud - Chapter 8: 

This module demonstrates three stages of a Spring Security OAuth2 implementation and consists of three sub-modules:

1. auth-server: The authorization server responsible for handling OAuth2 authorization.
2. resource-server: The resource server that serves protected resources.
3. client-app: The client application that interacts with both the authorization and resource servers.

All three modules are run sequentially in Docker containers to ensure proper initialization order. 
The wait-for-it scripts are added to enforce this sequence.

Steps to Run the Application
To run the application, follow these steps:

### Navigate to the module directory:
```
cd .\chap8-auth-resource-client\
```
### Build and start the Docker containers:
```bash
docker-compose up --build
```
### Stop containers
```bash
docker-compose down
```
### Access the application:
Once the containers are up and running, open your browser and go to http://localhost:9090. 
Follow the on-screen instructions to interact with the application.

###### username: habuma
###### password: password
or
###### username: tacochef
###### password: password



