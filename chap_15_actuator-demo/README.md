## Taco Cloud - Chapter 15: 
## *Working with Spring Boot Actuator*

### Use MongoDB Shell:
#### 1. Download and install MongoDB Shell: https://www.mongodb.com/try/download/shell
#### 2. Run MongoDB in docker:
```bash
docker run --rm --name my-mongo -p 27017:27017 -d mongo:latest
```
#### 3. Connect to the container: open your terminal or command prompt and execute the command:
```bash
mongosh --host localhost --port 27017
```
#### 4. Execute commands:
Here is the link to MongoDB Shell Commands: The Complete Cheat Sheet:
https://www.slingacademy.com/article/mongodb-shell-commands-the-complete-cheat-sheet/

### Build and run the project:
```bash
cd chap_15_actuator-demo
./mvnw spring-boot:run
```

### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

$ curl -X POST -d "TACOS1234567" -H "Content-type: text/plain" http://localhost:8080/api/discount
Discount code updated to: TACOS1234
Generating CSRF token/Turning off CSRF is needed.

curl 'http://localhost:8080/management/env/tacocloud.discount.code' -i -X GET

curl localhost:8080/management/mappings | jq

curl localhost:8080/management/loggers  | jq

curl localhost:8080/management/loggers/tacos.ingredients  | jq

curl localhost:8080/management/threaddump  | jq

curl localhost:8080/management/metrics  | jq

curl localhost:8080/management/metrics/http.server.requests | jq

curl localhost:8080/management/metrics/http.server.requests?  tag=status:200

curl localhost:8080/management/metrics/http.server.requests?  tag=status:200&tag=uri:/design

mvn spring-boot:build-info
curl localhost:8080/management/info | jq
