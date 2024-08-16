## Taco Cloud - Chapter 12: Developing reactive APIs

### Use MongoDB Shell:
#### 1. Download and install MongoDB Shell: https://www.mongodb.com/try/download/shell
#### 2. Run MongoDB Shell in docker:
```bash
docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest
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
cd chap_12_spring_webflux_demo
./mvnw spring-boot:run
```

### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

