## Taco Cloud - Chapter 15: 
## *Working with Spring Boot Actuator*

### 1. Run MongoDB in docker:
```bash
docker run --rm --name my-mongo -p 27017:27017 -d mongo:latest
```
### 2. Connect to the container: open your terminal or command prompt and execute the command:
```bash
mongosh --host localhost --port 27017
```

### 3. Run the project:
```bash
cd chap_15_actuator-demo
./mvnw spring-boot:run
```

### 4. Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

### 5. Execute the following requests:
#### *Some requests may require generating a CSRF token or disabling CSRF protection.*

$ curl -X POST -d "TACOS1234567" -H "Content-type: text/plain" http://localhost:8080/api/discount
Discount code updated to: TACOS1234


$ curl 'http://localhost:8080/management/env/tacocloud.discount.code' -i -X GET

$ curl localhost:8080/management/mappings | jq

$ curl localhost:8080/management/loggers  | jq

$ curl localhost:8080/management/loggers/tacos.ingredients  | jq

$ curl localhost:8080/management/threaddump  | jq

$ curl localhost:8080/management/metrics  | jq

$ curl localhost:8080/management/metrics/http.server.requests | jq

$ curl localhost:8080/management/metrics/http.server.requests?  tag=status:200
$ curl localhost:8080/management/metrics/http.server.requests?  tag=status:200&tag=uri:/design

```bash
mvn spring-boot:build-info
```
$ curl localhost:8080/management/info | jq

$ curl localhost:8080/management/health | jq

$ curl localhost:8080/management/env | jq '.propertySources[] | select(.name == "server.ports")'
$ curl localhost:8080/management/env | jq '.propertySources[] | select(.name == "server.ports") | .properties["local.server.port"].value'


$ curl localhost:8080/management/notes |jq

$ curl -X POST localhost:8080/management/notes \
-H "Content-Type: application/json" \
-d '{"text": "First Note"}'
