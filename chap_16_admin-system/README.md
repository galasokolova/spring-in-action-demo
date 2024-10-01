## Taco Cloud - Chapter 16: 
## *Administering Spring*

### 1. Run MongoDB in docker:
```bash
docker run --rm --name my-mongo -p 27017:27017 -d mongo:latest
```
### 2. Connect to the container: open your terminal or command prompt and execute the command:
```bash
mongosh --host localhost --port 27017
```

### 3. Run admin-server
### 4. Run Clients (Non-Reactive and Reactive)
You can run either one client or both clients simultaneously. Make sure that the clients are configured to use different ports:
* Non-Reactive Client: http://localhost:8081
* Reactive Client: http://localhost:8082

### 5. Access Admin Server and Clients: 
* http://localhost:9090 (server-admin)
* http://localhost:8081 (client-non-reactive)
* http://localhost:8082 (client-reactive)


###### Important: WackoHealthIndicator and Service Availability
This project includes a custom health indicator, ***WackoHealthIndicator***,
which demonstrates dynamic health checks and is part of the demonstration.

What does ***WackoHealthIndicator*** do?

After noon (12:00 PM): The health indicator marks the application as
* OUT_OF_SERVICE with the reason: "I'm out of service after lunchtime."
* Random failures (10% of the time):
  There is a 10% chance that the application will be marked as DOWN due to random failures.
  This simulates instability to demonstrate the monitoring capabilities of Spring Boot Admin.

###### Why is this important?
If you see the client marked as OUT_OF_SERVICE or DOWN in the Spring Boot Admin console, 
it is most likely due to this health indicator. This is intentional and part of the demonstration. 
The application is working as expected, but it will show as unavailable depending on the time of day or random chance.

###### How to test or disable the behavior
To ensure stable behavior for testing or running the application, 
you can temporarily disable the WackoHealthIndicator by commenting it out or modifying its logic. 
However, for demonstration purposes, it's recommended to leave it enabled to showcase Spring Boot Admin's handling of service outages.
