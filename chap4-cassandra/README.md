## 🌮 Taco Cloud — Chapter 4: Working with nonrelational data — Cassandra DB
This module implements the following part of the Chapter 4:
- **4.1** – Working with Cassandra repositories



### Prerequisites
- Docker
<br> 🧪 This module uses Apache Cassandra as the database, running inside a Docker container for convenience and portability.



### 🚀 How to run the project:
<div style="margin-left:60px">

#### ⚙️ Setting Up Cassandra DB

##### Step 1: Start Cassandra in Docker:
<div style="margin-left:60px">

```bash
     docker run --rm -d --name cassandra --hostname cassandra -p 9042:9042 cassandra
```
🕒 Note: Wait ~30–40 seconds for Cassandra to fully initialize before proceeding.

</div>

#### Step 2: Open cqlsh:
<div style="margin-left:60px">

```bash
     docker exec -it cassandra cqlsh
```
</div>


#### Step 3: Create KEYSPACE "taco_cloud":
<div style="margin-left:60px">
Once you are in the CQLSH shell, execute the following CQL command to create the keyspace:

```sql
 CREATE KEYSPACE IF NOT EXISTS taco_cloud
WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor': 1
};
```
</div>

#### Step 4: Build and run the project:
<div style="margin-left:60px">

```bash
     cd chap4-cassandra
     ./mvnw spring-boot:run
```
</div>



### 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.
<br> You’ll see the home page served by the HomeController.
#### 🚧 Navigation Note
Since authorization is not covered in Chapter 2, 
this project does not include navigation buttons from the home page to other sections.
To access the Taco Design page, you'll need to manually enter the following URL in your browser:
```
http://localhost:8080/design
```
Once you've selected your ingredients and named your taco, clicking "Submit Your Taco" will redirect you to the order form page.

After completing the order form and clicking "Submit Order", you'll be redirected back to the home page.

### Requests to Cassandra DB:

Here are some basic CQL queries you can run in cqlsh after connecting to the taco_cloud keyspace:

📌 Use the keyspace (if not already active):
```
USE taco_cloud;
```
🔍 View all ingredients:
```
SELECT * FROM ingredients;
```
🔍 View all tacos:
```
SELECT * FROM tacos;
```
🔍 View all taco orders:
```
SELECT * FROM orders;
```
🔍 View all id:
```
SELECT id, tacos FROM orders;
``` 

</div>

### Stop Cassandra container:
<div style="margin-left:40px">

```bash
docker stop cassandra
```

</div>





