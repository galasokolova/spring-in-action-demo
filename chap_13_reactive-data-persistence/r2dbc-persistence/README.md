## Taco Cloud - Chapter 13: 
### *13.1 Working with R2DBC*

### Run PostgreSQL container in docker:
```bash
docker run --name my-postgres -e POSTGRES_DB=tacocloud -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15
```

### Build and run the project:
```bash
cd .\chap_13_reactive-data-persistence\r2dbc-persistence\
./mvnw spring-boot:run
```
### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.






