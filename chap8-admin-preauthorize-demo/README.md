# 🌮 Taco Cloud — Chapter 8: Securing REST
## 8.1 "Introducing OAuth 2"

This module demonstrates how to secure RESTful endpoints using Spring Security and role-based access control.

It is based on **Section 8.1 "Introducing OAuth 2"** from *Spring in Action*, 
but the focus here is **restricting access to API endpoints via `@PreAuthorize`** for users with specific roles (e.g., `ADMIN`).

---

### 🔐 Security Configuration Highlights:

* **Custom security configuration** with `SecurityFilterChain`, supporting both `USER` and `ADMIN` roles.
* **Two separate user providers**: one for regular users, another for administrators.
* **REST endpoints** secured with `@PreAuthorize("hasRole('ADMIN')")` for sensitive operations.
* **CSRF protection is disabled** to simplify REST testing in this educational demo.

> ⚠️ **Important**: CSRF protection is **disabled** (`http.csrf().disable()`) to allow testing with tools like `curl` or Postman.
> This is acceptable in **non-production environments only**. Never disable CSRF in real-world web applications that use cookies or sessions.

---

### 🧠 Data Initialization

Upon startup, the application:

* Loads initial ingredient data from `ingredient.json`.
* Creates two users:

    * A regular user:

        * **Username**: `user`
        * **Password**: `1234`
    * An admin:

        * **Username**: `admin`
        * **Password**: `1234`

This logic is implemented in the `CommandLineRunner` in the class `Chap8AdminPreauthorizeDemoApplication`.

---

### 🧲 Try it out

#### 🔨 Build and run the project:

```bash
    cd chap8-admin-preauthorize-demo
    ./mvnw spring-boot:run
```

---

### 🔗 Access the application

* App entry point: [http://localhost:8080](http://localhost:8080)
* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

    * **Username**: `sa`
    * **Password**: `password`

---

### 📦 API Usage

#### ✅ Get all ingredients (no auth required):

```bash
curl http://localhost:8080/api/ingredients
```

#### ✅ Get a specific ingredient (no auth required):

```bash
curl http://localhost:8080/api/ingredients/FLTO
```

#### 🔐 Add a new ingredient (requires ADMIN login):

```bash
curl -X POST http://localhost:8080/api/ingredients \
  -H "Content-Type: application/json" \
  -u admin:1234 \
  -d '{"id":"FISH","name":"Stinky Fish","type":"PROTEIN"}'
```

#### 🔐 Delete an ingredient (requires ADMIN login):

```bash
curl -X DELETE http://localhost:8080/api/ingredients/CHED \
  -u admin:1234
```

---

### ✅ Summary

This module is designed for **educational purposes** to showcase:

* Role-based access to REST endpoints.
* Usage of `@PreAuthorize` for endpoint-level security.
* User/Admin separation using different authentication providers.

In a real-world application:

* CSRF should be **enabled**.
* Authentication should use **OAuth2**, JWT, or session management depending on the context.
