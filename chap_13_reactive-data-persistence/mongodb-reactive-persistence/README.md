## Taco Cloud - Chapter 13: 
## *13.2 Persisting document data reactively with MongoDB*

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
cd chap_13_reactive-data-persistence/mongodb-reactive-persistence
./mvnw spring-boot:run
```

### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

## Submodule Description: mongodb-reactive-persistence
This submodule is almost identical to the module chap_13_reactive-data-persistence, but there is one key difference in the data model.

### Main Difference:
* ***chap_13_reactive-data-persistence:*** 
In this module, the Taco class is represented as a standalone document in the MongoDB database.
Each Taco is stored separately and independently of other data.

* ***mongodb-reactive-persistence:***
In this submodule, the Taco class is embedded within the TacoOrder class, 
making it part of the TacoOrder aggregate. 
As such, each Taco is stored in the database as part of a TacoOrder, rather than as a separate document.

This change reflects a different data storage architecture, where Taco is stored in context as part of a TacoOrder,
rather than as an independent entity. 
This structure allows for more cohesive data management and supports the integrity of aggregates in MongoDB.

Use Case:
This difference can be beneficial when related data needs 
to be stored together to ensure data consistency and optimize read and write operations in MongoDB.

