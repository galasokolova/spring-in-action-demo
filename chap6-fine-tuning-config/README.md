# 🌮 Taco Cloud – Chapter 6: Fine-Tuning Spring Boot Configuration

This module demonstrates the key techniques from **Chapter 6 – Section 6.1** of *Spring in Action*:

- How to enable HTTPS using a self-signed certificate.
- How to configure access to the H2 console.
- How to expose an RSocket server with SSL.

---

## 🔐 Steps to Configure HTTPS

### ✅ Step 1: Create a Keystore

Use this to generate a new keystore file (`.jks`) that holds a self-signed certificate.

```bash
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks
```

📝 This creates:

- A key pair (public/private)
- A self-signed certificate
- Stored in `keystore.jks` using alias `tomcat`

---

### ✅ Step 2: Add a Certificate (Optional)

If you have an external certificate (e.g., from a CA), you can import it into your keystore:

```bash
keytool -importcert -alias mycert -file certificate.crt -keystore keystore.jks
```

📝 This adds the signed certificate to your existing keystore.

---

### ✅ Step 3: View Keystore Contents

Check what's inside your keystore:

```bash
keytool -list -keystore keystore.jks
```

📝 You’ll see all aliases and certificates stored inside. Use it to verify the keystore was created correctly.

---

### ✅ Step 4: Change Keystore Password

Secure your keystore by changing its password if needed:

```bash
keytool -storepasswd -keystore keystore.jks
```

📝 You’ll be prompted to enter the old and new passwords.

---

## ⚙️ Step 5: Configure `application.yml`

Update your Spring Boot config to:

- Enable HTTPS on port 8443
- Use the keystore for SSL
- Enable H2 console
- Configure RSocket with SSL

```yaml
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:keystore.jks
    key-store-password: your-store-password
    key-password: your-password
    key-store-type: PKCS12
    key-alias: mycert

spring:
  devtools:
    restart:
      enabled: true

  datasource:
    generate-unique-name: false
    name: tacocloud
    url: jdbc:h2:mem:tacocloud
    username: sa
    password: password
    driver-class-name: org.h2.Driver

  h2:
    console:
      enabled: true
      path: /h2-console

  rsocket:
    server:
      port: 8444
      ssl:
        key-store: "classpath:keystore.jks"
        key-store-password: "your-store-password"
        key-password: "your-password"
        key-store-type: PKCS12
```

---

## ✅ Step 6: Configure Spring Security

Ensure HTTPS and endpoint protection is compatible with the new setup. Update your security config accordingly (not shown here).

---

## ▶️ Step 7: Run the Application

Run the main class:

```bash
./mvnw spring-boot:run
```

🔗 App available at: [https://localhost:8443](https://localhost:8443)\
🧲 H2 Console at: [https://localhost:8443/h2-console](https://localhost:8443/h2-console)

---

## ✅ Summary

This module shows how to:

- Secure a Spring Boot app with HTTPS
- Manage keystore and certificates
- Set up secure RSocket communication


