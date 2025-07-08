# 🌮 Taco Cloud – Chapter 7: Creating RESTful Services

This module covers **Chapter 7 – Section 7.1** from *Spring in Action*, 
demonstrating how to implement a full-featured REST API using Spring MVC.

---

## ✅ Topics Covered

### 7.1.1 Retrieving Data from the Server

* `GET /api/tacos?recent` — retrieves the 9 most recent tacos, sorted by `createdAt`.
* `GET /api/tacos/{id}` — retrieves a taco by ID.
* `GET /api/ingredients/{id}` — retrieves an ingredient by ID.

### 7.1.2 Sending Data to the Server

* `POST /api/tacos` — creates a new taco from JSON request body.
* `POST /api/ingredients` — creates a new ingredient.

### 7.1.3 Updating Data on the Server

* `PUT /api/tacos/{orderId}` — completely replaces an order with the provided data.
* `PATCH /api/tacos/{orderId}` — partially updates fields in an existing order.

### 7.1.4 Deleting Data from the Server

* `DELETE /api/tacos/{orderId}` — deletes an order by ID.
* `DELETE /api/ingredients/{id}` — deletes an ingredient.

---

## 🧠 Highlights

* Use of `@RestController` to build REST endpoints.
* Handling CORS with `@CrossOrigin`.
* Use of `@ExceptionHandler` for custom exception handling.
* Graceful error responses using `ResponseEntity` and status codes.
* Use of `PageRequest` and `Sort` for pagination and sorting.
* Input validation and partial updates in PATCH.

---

## ▶️ Build and Run the Project

```bash
    cd chap7-restful-controller
    ./mvnw spring-boot:run
```

---

## 🌐 Access the Application

Visit: [http://localhost:8080](http://localhost:8080)

---

## 🧪 H2 Database Console

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Username: `sa`
* Password: `password`


