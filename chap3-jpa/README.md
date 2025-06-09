## 🌮 Taco Cloud – Chapter 3: Working with data — JPA

This module implements the following part of the Chapter 3:
- **3.3** – Persisting data with Spring Data JPA

### 🧠 What this module demonstrates:
- Configuring JPA with Spring Boot
- Using an H2 in-memory database for rapid development and testing
- Populating the database with Ingredients (data.sql)
- Persisting and retrieving Taco designs and orders from a relational database

### 🛠 Technologies used:
- Spring Boot 3
- Spring MVC
- Thymeleaf
- JPA
- H2

### 🚀 How to run the project:
#### Option 1: From terminal
```bash
    cd chap3-jpa
    ./mvnw spring-boot:run
```
#### Option 2: From IDE
You can also run the application by executing the main() method in the Chap3JpaApplication class:
```
src/main/java/pt/galina/chap3jpa/Chap3JpaApplication.java
```


### 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.
<br> You’ll see the home page served by the HomeController.
#### 🚧 Navigation Note
Since authorization is not covered in Chapter 2, this project does not include navigation buttons from the home page to other sections.

To access the Taco Design page, you'll need to manually enter the following URL in your browser:
```
http://localhost:8080/design
```
Once you've selected your ingredients and named your taco, clicking "Submit Your Taco" will redirect you to the order form page.

After completing the order form and clicking "Submit Order", you'll be redirected back to the home page.


### 🛢  H2 Database Console

✅ The JDBC URL jdbc:h2:mem:testdb is configured in application.yml.
* URL: http://localhost:8080/h2-console
* Username: sa
* Password: password

Once you're logged in, you can use the following SQL queries to inspect the database:

📋 View all taco orders:
```
SELECT * FROM taco_order;
```
🌮 View all tacos:
```
SELECT * FROM taco;
```

⚠️ Note: If you're using an in-memory database (H2), all data will be lost after application shutdown.

