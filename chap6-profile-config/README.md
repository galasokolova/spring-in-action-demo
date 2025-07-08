# 🌮 Taco Cloud — Chapter 6: Profile-Specific Configuration and Advanced Logging

This module demonstrates key concepts from **Chapter 6** of *Spring in Action*:

* ✅ **6.1.4**: Configuring logging with `logback-spring.xml`
* ✅ **6.2**: Using `@ConfigurationProperties` for strongly typed configuration
* ✅ **6.3.1**: Defining and activating profile-specific properties

---

## 🧰 What’s Implemented

### 🔧 Custom Logback Configuration

A `logback-spring.xml` file is used instead of application.yml to manage logging behavior:

* Console logs filtered at `WARN` level
* File logs rolled by size and date
* Security logs logged separately at `DEBUG` level

### 🔐 Profile-Specific Settings

Two profiles are configured:

* **`default`**: Used in production or general use
* **`dev`**: Overrides default settings with development-specific configs

Profile-specific files:

* `application.yml`: base configuration
* `application-dev.yml`: development profile

Activated via:

```yaml
spring:
  profiles:
    active: dev
```

### 📦 ConfigurationProperties

Class `OrderProps` is annotated with `@ConfigurationProperties` and `@Validated`, allowing type-safe and validated access to `taco.orders.page-size`.

---

## ▶️ Build and Run

```bash
    cd chap6-profile-config
    ./mvnw spring-boot:run
```

---

## 🌐 Access the Application

* URL: [http://localhost:8080](http://localhost:8080)
* Login with standard credentials or OAuth2 (if configured)

---

## 🧲 H2 Console

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Username: `sa`
* Password: `password`

---

## 🧠 Summary

This module demonstrates how to:

* Use Logback for flexible and powerful logging
* Externalize configuration using `@ConfigurationProperties`
* Manage multiple environments using Spring Profiles



