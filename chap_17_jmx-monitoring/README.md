## Taco Cloud - Chapter 17: 
## *Monitoring Spring with JMX*

*This module demonstrates the use of JMX for monitoring 
and managing the Taco Cloud application using Spring Boot Actuator and JMX. 
The application publishes notifications through JMX when new Tacos are created 
and tracks the total count of tacos.*

* JMX Monitoring: 
The application automatically registers an MBean that tracks the number of tacos 
created and sends notifications when certain thresholds are met.

* Actuator Endpoints: The MBean provides information about the application state via Actuator.

* Notifications: Every second taco created triggers a notification through JMX 
using the TacoCounter component.

### 1. Run MongoDB in docker:
```bash
docker run --rm --name my-mongo -p 27017:27017 -d mongo:latest
```
### 2. Run the Application:
```bash
cd chap_17_jmx-monitoring
```
```bash
./mvnw spring-boot:run
```

### 4. Monitor with JMX:
* Open JConsole (or any other JMX client) and connect to your process.
```bash
jconsole
```
* Find TacoCounter in the org.springframework.boot domain to track the number of tacos 
and receive notifications.

### 5. Create a Taco: 
Go to http://localhost:8080 and create a taco through the web interface. 
Each new taco will increment the counter and trigger a notification via JMX.
After every 2 tacos created, a notification will be sent via JMX.