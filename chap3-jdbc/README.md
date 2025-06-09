## 🌮 Taco Cloud – Chapter 3: Working with data—JDBC

This module implements the following parts of the Chapter 3:
- **3.1** – Reading and writing data with JDBC
- **3.2** – Working with Spring Data JDBC

### 🧠 What this module demonstrates:
- Configuring JDBC with Spring Boot
- Using an H2 in-memory database for rapid development and testing
- Populating the database at startup using a CommandLineRunner bean
- Persisting and retrieving Taco designs and orders from a relational database
- Structuring domain models (Taco, TacoOrder, Ingredient) for JDBC-based persistence
- Connecting the controller logic to real data instead of hardcoded lists

### 🛠 Technologies used:
- Spring Boot 3
- Spring MVC
- Thymeleaf
- JDBC
- H2

### 🚀 How to run the project:
#### Option 1: From terminal
```bash
    cd chap3-jdbc
    ./mvnw spring-boot:run
```
#### Option 2: From IDE
You can also run the application by executing the main() method in the Chap2Application class:
```
src/main/java/pt/galina/chap3jdbc/Chap3JdbcApplication.java
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

After completing the order form and clicking "Submit Order" you'll be redirected back to the home page.


### 🌱 Data Initialization
To populate the database with sample ingredient data, this module uses a CommandLineRunner bean. 
When the application starts, it automatically saves a predefined list of Ingredient objects via the IngredientRepository.
This ensures that the design form has all required options available without needing manual data entry or SQL scripts.

The data is inserted only once at startup and includes items like:
- Flour Tortilla
- Corn Tortilla
- Ground Beef
- Cheddar
- Salsa
- (and others)

Relevant code snippet (in Chap3JdbcApplication.java):
```
@Bean
public CommandLineRunner dataLoader(IngredientRepository repo) {
return args -> {
repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
// ... more ingredients ...
};
}
```


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

