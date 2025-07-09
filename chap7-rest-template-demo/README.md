## 🌮 Taco Cloud — Chapter 7: Enabling Data-Backed REST Services

This module demonstrates how to expose Spring Data repositories as RESTful services using **Spring Data REST**, and how to fine-tune REST configuration.

---

### ✅ Covered Topics

* **7.2** Enabling data-backed services with Spring Data REST
* **7.2.1** Adjusting resource paths and relation names
* **7.2.2** Paging and sorting of data

---

### ⚙️ Key Features and Configurations

✅ **Spring Data REST**

* Automatically exposes CRUD operations for JPA repositories as REST endpoints.
* Uses HAL format by default.

✅ **Custom REST base path**
Defined in `application.yml`:

```yaml
spring:
  data:
    rest:
      base-path: /api
```

This means all REST endpoints are served from `/api/**`.

✅ **Spring Integration Logging (Optional)**

```yaml
spring:
  integration:
    management:
      default-logging-enabled: true
```

This enables detailed logs for Spring Integration components.

✅ **RestTemplate Configuration**

```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

Defined in `RestTemplateConfig.java` to allow internal REST calls within the application.

✅ **Dependency Added**

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

---

### 🚀 Build and Run the Project

```bash
cd chap7-rest-template-demo
./mvnw spring-boot:run
```

---

### 🌐 Access the Application

* Base URL: [http://localhost:8080](http://localhost:8080)
* REST API root: [http://localhost:8080/api](http://localhost:8080/api)

### 🧪 H2 Database Console

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Username: `sa`
* Password: `password`

---

### 📡 Example API Requests

🧭 View available REST resources:

```bash
curl http://localhost:8080/api
```

📄 Get the first page of tacos (default size):

```bash
curl "http://localhost:8080/api/tacos"
```

📄 Get tacos with page size = 5:

```bash
curl "http://localhost:8080/api/tacos?size=5"
```

📄 Get second page (page index = 1):

```bash
curl "http://localhost:8080/api/tacos?size=5&page=1"
```

📄 Sort by `createdAt` descending, show 12 most recent:

```bash
curl "http://localhost:8080/api/tacos?sort=createdAt,desc&page=0&size=12"
```

---

### ✅ Summary

This module shows how easy it is to:

* Expose data repositories as RESTful endpoints
* Use paging and sorting
* Customize API paths
* Add `RestTemplate` for REST calls
