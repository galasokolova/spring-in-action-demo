## 🌮 Taco Cloud – Chapter 5: Securing Spring — In-memory user details service demo

This module implements the following part of the Chapter 5:
- **5.2.1** – In-memory user details service

This section introduces the basic concepts of authentication and how to configure an in-memory user store using Spring Security.


### 🧠 What this module demonstrates:

- Basic Spring Security configuration with SecurityFilterChain
- Defining a custom UserDetailsService using InMemoryUserDetailsManager
- Encoding passwords using BCryptPasswordEncoder
- Creating user accounts with roles in-memory
- Protecting access to a simple home page with a login form

Upon starting the application, users are prompted to log in with one of the predefined credentials.
After successful authentication, the home page is displayed.

### 🛠 Technologies used:
- Spring Boot 3
- Spring Security
- Thymeleaf (basic view rendering only — no MVC/controllers beyond a static home page)

### 🚀 How to run the project:
#### Option 1: From terminal
```bash
    cd chap5-in-memory-user
    ./mvnw spring-boot:run
```
#### Option 2: From IDE
You can also run the application by executing the main() method in the Chap5InMemoryUserApplication class:
```
src/main/java/pt/galina/chap5inmemoryuser/Chap5InMemoryUserApplication.java
```


## 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 .

Upon starting the application, users are prompted to log in with one of the predefined credentials.
After successful authentication, the home page is displayed.

| Username  | Password    | Role         |
|-----------|-------------|--------------|
| `buzz`    | password    | `ROLE_USER`  |
| `woody`   | password    | `ROLE_USER`  |


