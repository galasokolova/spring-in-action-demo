# 🌮 Taco Cloud — Chapter 10: Integrating Spring
## 10.1 Declaring a simple integration flow
## 10.2 Surveying the Spring Integration landscape
## 10.3 Creating an email integration flow

This module demonstrates integrating a mail server with a REST API to process taco orders received via **email**.

It consists of two Spring Boot microservices:

- `tacocloud-api` — A REST API that accepts taco orders via HTTP POST and stores them in memory.
- `tacocloud-email-handler` — A background service that polls an email inbox, 
                               parses taco orders from incoming messages, and sends them to the REST API.

---

## 🧠 How It Works

1. The email handler polls a Gmail inbox every 10 seconds.
2. When it finds a new message with the subject `"taco order"`, it extracts the content and parses the taco details.
3. The extracted data is sent to the API via an HTTP POST request.
4. The REST API logs and stores the order in memory.
5. The web client can view all orders at:
   ```
   http://localhost:8080/api/orders
   ```

---

## ⚙️ Setup Instructions

### 1. 🔑 Configure Email Access

In the `application.yml` of the `tacocloud-email-handler`, set up your Gmail credentials:

```yaml
tacocloud:
  email:
    host: imap.gmail.com
    port: 993
    username: your_email_username
    password: your_app_specific_password
    mailbox: INBOX
    poll-rate: 10000
```

> 💡 The password must be a **Google App Password**, not your normal Gmail password.  
> You must enable IMAP in Gmail settings and allow [App Passwords](https://support.google.com/accounts/answer/185833?hl=en).

---

### 2. ▶️ Run the Applications

Start each module separately, for example:

```bash
   cd tacocloud-api
   mvn spring-boot:run
```

```bash
   cd tacocloud-email-handler
   mvn spring-boot:run
```

---

### 3. ✉️ Send a Taco Order Email

From any email client, send a message to your configured Gmail address:

- **Subject:** `taco order`  
- **Body:**
  ```
  TacoOne: FLTO, TOMATOES, CHEDDAR, SALSA
  TacoTwo: COTO, LETTUCE, GRBF, SRCR
  TacoThree: FLTO, CARN, TMTO, SLSA
  ```

> The format must be:  
> `TacoName: ING1, ING2, ING3`

---

### 4. 🧪 View Received Orders

Visit:  
[http://localhost:8080/api/orders](http://localhost:8080/api/orders)  

Once processed, the email will be marked as **read**.

---

## 🧩 Key Classes Overview

### 📦 `tacocloud-api`
- `OrderController` — handles REST endpoints to accept and expose taco orders.
- `KitchenIntegrationConfig` — defines an integration flow that sends orders to `/api/orders`.
- `RestTemplateConfig` — provides a `RestTemplate` bean for outbound HTTP calls.

### 📧 `tacocloud-email-handler`
- `TacoOrderEmailIntegrationConfig` — defines an `IntegrationFlow` that acts as the email poller, connecting to Gmail via IMAP, extracting, transforming, and forwarding taco orders.
- `EmailToOrderTransformer` — parses valid emails (with subject `taco order`) into structured `EmailOrder` objects.
- `EmailContentExtractor` — extracts and normalizes content from MIME messages (text/plain and text/html), using JSoup.
- `OrderSubmitMessageHandler` — sends the parsed order via REST to the API.
- `EmailProperties` — maps configuration from `application.yml` (IMAP credentials, polling interval, etc.).
- `RestTemplateConfig` — provides a `RestTemplate` bean for REST communication.





