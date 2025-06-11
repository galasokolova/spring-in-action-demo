## 🌮 Taco Cloud – Chapter 5: Securing Spring — CSRF

This module implements the following part of the Chapter 5:
- **5.3.4** – Preventing cross-site request forgery (CSRF)

This section demonstrates CSRF protection as described in Spring in Action, Sixth Edition.

        ⚠️ Note: Since the project uses a newer version of Spring Security, 
            some methods and configuration styles differ from the book's examples.

### 🧠 What this module demonstrates:
- Configuring Spring Security in a Spring Boot application
- Enabling or disabling CSRF protection
- Implementing user registration

### 🛠 Technologies used:
- Spring Boot 3
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database

### 🚀 How to run the project:
#### Option 1: From terminal
```bash
    cd chap5-csrf-demo
    ./mvnw spring-boot:run
```
#### Option 2: From IDE
You can also run the application by executing the main() method in the Chap5CsrfDemoApplication class:
```
src/main/java/pt/galina/chap5csrfdemo/Chap5CsrfDemoApplication.java
```
#

### 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

### 🛢 H2 Database Console

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

