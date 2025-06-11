## 🌮 Taco Cloud — Chapter 4: Working with nonrelational data — MongoDB
This module implements the following part of the Chapter 4:
- **4.2** –  Writing MongoDB repositories


### Prerequisites
- Docker
  <br> 🧪 This module uses MongoDB as the database, running inside a Docker container for convenience and portability.


### 🚀 How to run the project:
<div style="margin-left:60px">

##### Step 1: Start MongoDB in Docker (MongoDB with Clean Startup):
<div style="margin-left:60px">

```bash
     docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest
```
✅ No data will persist — *every run starts with a clean database*.
</div>


#### Step 2: Build and run the project:
<div style="margin-left:60px">

```bash
     cd chap4-mongodb
     ./mvnw spring-boot:run
```
</div>

</div>

### 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.
<br> You’ll see the home page served by the HomeController.
#### 🚧 Navigation Note
Since authorization is not covered in Chapter 4,
this project does not include navigation buttons from the home page to other sections.
To access the Taco Design page, you'll need to manually enter the following URL in your browser:
```
http://localhost:8080/design
```
Once you've selected your ingredients and named your taco, clicking "Submit Your Taco" will redirect you to the order form page.
After completing the order form and clicking "Submit Order", you'll be redirected back to the home page.

## 🛢️ MongoDB Setup
This project is configured for learning purposes, so the MongoDB container is started with --rm, 
meaning all data is ephemeral (deleted after shutdown). 
The database is automatically repopulated when the Spring Boot app starts.

### 🔧 Option 1: Use Mongo Express (Web GUI):
```bash
  docker run --rm --name mongo-express-container --link my-mongo-container:mongo -p 8081:8081 -e ME_CONFIG_MONGODB_SERVER=mongo -e ME_CONFIG_BASICAUTH_USERNAME=myUser -e ME_CONFIG_BASICAUTH_PASSWORD=myPassword -d mongo-express
```
Mongo Express will be available on http://localhost:8081

| **Login credentials:** |               |
|------------------------|---------------|
| username:              | myUser        |
| password:              | myPassword    |

### 🐚 Option 2: Use MongoDB Shell (mongosh) :
#### Step 1: Run MongoDB Shell in docker if you haven't run it yet:
```bash
  docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest
```
#### Step 2: Connect using MongoDB Shell (mongosh) :
If you have mongosh installed locally, run:

```bash
  mongosh --host localhost --port 27017
```  
  If not installed, download it here:

🔗 https://www.mongodb.com/try/download/shell   

#### Step 3: Execute useful commands:
Examples:

#### 🧰 Brief MongoDB Shell Cheat Sheet

| 🧩 Task                                                                | 💻 Command                                                                           |
|------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| 🔌 Start MongoDB in Docker                                             | `docker run --rm --name my-mongo-container -p 27017:27017 -d mongo:latest`           |
| 🔗 Connect to MongoDB (with mongosh)                                   | `mongosh --host localhost --port 27017`                                              |
| ⛔ Stop MongoDB container                                               | `docker stop my-mongo-container`                                                     |
| 🔄 Switch to database `tacoDB`                                         | `use tacoDB`                                                                         |
| 📚 Show all databases                                                  | `show dbs`                                                                           |
| 📁 Show all collections                                                | `show collections`                                                                   |
| 🔍 View documents in `ingredients` collection                          | `db.ingredients.find().pretty()`                                                     |
| 🕒 Add `createdAt` field with current date to all documents in `tacos` | `db.tacos.updateMany({}, { $set: { createdAt: new Date() } })`                       |
| 📌 Switch to another DB (e.g. `taco_cloud`)                            | `use taco_cloud`                                                                     |
| 💣 Drop entire DB `tacoDB`                                             | `use tacoDB`<br>`db.dropDatabase()`                                                  |
| 🧹 Drop collection `collection_name`                                   | `db.collection_name.drop()`                                                          |
| 🧨 Drop **all** collections in current DB                              | `db.getCollectionNames().forEach(function(collection) { db[collection].drop(); });`  |
| ❌ Remove `createdAt` field from all documents in `tacos`               | `db.tacos.updateMany({}, { $unset: { createdAt: "" } })`                             |
| ✅ Confirm deletion (view one document)                                 | `db.tacos.findOne()`                                                                 |


For more commands, see **MongoDB Shell Commands — The Complete Cheat Sheet**


🔗https://www.slingacademy.com/article/mongodb-shell-commands-the-complete-cheat-sheet/