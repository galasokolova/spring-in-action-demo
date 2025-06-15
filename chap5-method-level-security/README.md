## 🛡️ Taco Cloud – Chapter 5: Securing Spring — Method-Level Security

This module implements **Chapter 5.4 – Method-Level Security** from the book.

It demonstrates how to restrict access to specific service-layer methods using annotations like ***@PreAuthorize***.

---

### 🧠 What This Module Demonstrates:

* Enabling method-level security with ***@PreAuthorize*** on the `deleteAllOrders()` method in `OrderAdminService.java`
* Restricting sensitive operations (e.g., deleting all orders) to users with the `ADMIN` role
* Handling 403 errors with a custom error handler:

    * `src/main/java/pt/galina/chap5methodlevelsecurity/security/error`
    * `src/main/java/pt/galina/chap5methodlevelsecurity/security/SecurityConfig.java`

---

### 🛠 Technologies Used:

* Spring Boot 3
* Spring Security
* Spring Data JPA
* H2 Database
* Thymeleaf

---

### 📆 H2 Database

The H2 database is populated on startup via a `CommandLineRunner`.
Taco ingredients are loaded from a JSON file.
User and Admin accounts are hardcoded:

| Username   | Password    | Role           |
|------------|-------------|----------------|
| `user`     | 1234        | `ROLE_USER`    |
| `admin`    | 1234        | `ROLE_ADMIN`   |

---

### 🚀 How to run the project:
#### Option 1: From terminal
```bash
    cd chap5-method-level-security
    ./mvnw spring-boot:run
```
#### Option 2: From IDE

Run the application by executing the `main()` method in:

```
src/main/java/pt/galina/chap5methodlevelsecurity/Chap5MethodLevelSecurityApplication.java
```

---

### 🔍 How It Works

This example **intentionally allows** the user to access the admin page (to demonstrate method-level protection).
However, the user **cannot** delete orders — because the `deleteAllOrders()` method is protected by ***@PreAuthorize***.

Start at: [http://localhost:8080](http://localhost:8080)

**Steps:**

1. Log in as `admin`
2. Go to the admin page (via home page button)
3. Press **"Delete Orders"**
4. You’ll see a message: “All orders have been deleted!”
5. Logout
6. Log in as `user`
7. Go to the admin page
8. Press **"Delete Orders"**
9. You’ll see a **403 Forbidden** error message rendered via a custom error page.

---


