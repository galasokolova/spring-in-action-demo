## Taco Cloud - Chapter 4: Spring Data MongoDB

### Run MongoDB container in docker:
```bash
docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest
```

### Build and run the project:
```bash
cd chap4-mongodb
./mvnw spring-boot:run
```
### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

### Design tacos:
To design a taco, manually navigate to http://localhost:8080/design.

#

### Run Mongo Express container in docker:
```bash
docker run --rm --name mongo-express-container --link my-mongo-container:mongo -p 8081:8081 -e ME_CONFIG_MONGODB_SERVER=mongo -e ME_CONFIG_BASICAUTH_USERNAME=myUser -e ME_CONFIG_BASICAUTH_PASSWORD=myPassword -d mongo-express
```
Mongo Express will be available on http://localhost:8081

### OR

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




