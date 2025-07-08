# 📘 Taco Cloud — Chapter 6: Logging Configuration

## 🔧 6.1.4 Configuring Logging via `application.yml`
## 🔧 6.2 Creating your own configuration properties
This module demonstrates how to configure **logging** in a Spring Boot application 
using the `application.yml` file instead of traditional `logback.xml` or `log4j.properties`. 
It also shows how to externalize and validate configuration properties
using `@ConfigurationProperties`.

---

## 🔍 What this module shows

* Setting up log levels for different packages
* Writing logs to a file with a custom pattern
* Enabling detailed debug logs for Spring Security
* Specifying log format using `logging.pattern.file`
* Organizing logs in a `log/` directory
* Externalizing configuration using `@ConfigurationProperties`
* Validating configuration values using JSR-303 annotations (`@Min`, `@Max`)

---

## ⚙️ `application.yml` logging config used:

```yaml
logging:
  file:
    name: log/TacoCloud.log            # Log file location
  level:
    root: WARN                         # Default log level
    org.springframework.security: DEBUG  # More verbose logs for Spring Security
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"  # File log format

taco:
  orders:
    page-size: 10                      # Custom app property bound to ConfigurationProperties
```

---

## 📆 `OrderProps.java`

```java
@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
public class OrderProps {

    @Min(value = 1, message = "must be between 1 and 25")
    @Max(value = 25, message = "must be between 1 and 25")
    private int pageSize;

    public OrderProps(int pageSize) {
        this.pageSize = pageSize;
    }
}
```

This class binds the `taco.orders.page-size` value from `application.yml`, and validates that it's between 1 and 25.

---

## ▶️ How to Run the Project

```bash
    cd chap6-logging-config
    ./mvnw spring-boot:run
```

---

## 🌐 Access the Application

* Web app: [http://localhost:8080](http://localhost:8080)
* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

    * Username: `sa`
    * Password: `password`

---

## 📁 Where to find the logs

After running the application, logs will be written to:

```
log/TacoCloud.log
```

---

## ✅ Summary

This module shows how to:

* Securely configure and customize logging output
* Write logs to structured files
* Add custom validation on externalized properties
* Use `@ConfigurationProperties` to centralize app config


