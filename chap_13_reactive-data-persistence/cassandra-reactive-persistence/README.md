## Taco Cloud - Chapter 13: 
## *13.3 Reactively persisting data in Cassandra*

### Setting Up Cassandra DB

#### Step 1: Start Cassandra in Docker:
```bash
docker run --rm -d --name cassandra --hostname cassandra -p 9042:9042 cassandra
```
#### Step 1.1: (Wait for 30-40 seconds or so)

#### Step 2: Open cqlsh:
```bash
docker exec -it cassandra cqlsh
```

#### Step 3: Create KEYSPACE "taco_cloud":
Once you are in the CQLSH shell, execute the following CQL command to create the keyspace:
```sql
 CREATE KEYSPACE IF NOT EXISTS taco_cloud WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
```

#### Step 4: Build and run the project:
```bash
cd cd chap_13_reactive-data-persistence\cassandra-reactive-persistence
```

```bash
./mvnw spring-boot:run
```
#### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

#### Design tacos:
To design a taco, manually navigate to http://localhost:8080/design.

### Stop Cassandra container:
```bash
docker stop cassandra
```

