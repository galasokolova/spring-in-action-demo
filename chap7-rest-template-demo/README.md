## Taco Cloud - Chapter 7: Writing RESTful controllers

### Build and run the project:
```bash
cd chap7-rest-template-demo
./mvnw spring-boot:run
```
### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

### H2 database console:
* URL: http://localhost:8080/h2-console
* Username: sa
* Password: password

### requests:
* to get endpoints:
```
$ curl localhost:8080/api
```
* to request the first page of tacos where the page size is 5
```
$ curl "localhost:8080/api/tacos?size=5"
```
* to request the second page of tacos
```
$ curl "localhost:8080/api/tacos?size=5&page=1"
```
* to sort the resulting list by any property of the entity, for example, the 12 most recently created tacos for the UI to display
```
* $ curl "localhost:8080/api/tacos?sort=createdAt,desc&page=0&size=12"
```