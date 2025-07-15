## Taco Cloud — Chapter 9: Sending Messages Asynchronously
## *9.2: RabbitMQ Push Model*

This module demonstrates how to implement asynchronous messaging using **RabbitMQ** 
with the **Push Model**,
as described in *Spring in Action*, Chapter 9.

The `chap9-rabbitmq-demo` project consists of two Spring Boot microservices:

- `order-sender` — A client web application that sends taco orders via RabbitMQ.
- `order-receiver` — A backend service that listens to RabbitMQ and receives taco orders in real time.

All services are containerized with Docker, and startup coordination is handled using the `wait-for-it.sh`
script to ensure dependencies (like RabbitMQ) are ready before each application starts.

---
### 🧾 Scripts Overview:
1. #### /scripts/entrypoint.sh
   *  Ensures RabbitMQ is available before starting the Spring Boot app.
2. #### /scripts/wait-for-it.sh
   * A lightweight shell script to wait for a host/port to become available.  
     Each app uses this logic to prevent premature startup failures.
---

## ⚙️ How It Works — Container Logic

The `docker-compose.yml` defines the full system architecture:

1. **RabbitMQ** starts first.
2. **order-sender** and **order-receiver** both:
   - Wait for RabbitMQ to be reachable (using `wait-for-it.sh` in `entrypoint.sh`)

---
### Building and Running the Application
***Note!***
***Ensure you have Docker installed and running.***

1. Navigate to the module directory:
   ```bash
   cd ./chap9-rabbitmq-demo
   ```
2.  Build and start the Docker containers:
   ```bash
   docker-compose up --build
   ```

## 🧪 Messaging Logic (RabbitMQ Push Model)

Once everything is running:

1. Go to [http://localhost:8080](http://localhost:8080) to access `order-sender`.
2. Submit a taco order via the form and click "Send Order."
3. The app pushes the message to RabbitMQ instantly.
4. The `order-receiver` (already subscribed) receives the message and stores it in memory.
5. Go to [http://localhost:8081](http://localhost:8081) and click **"View Received Orders"** to see them.

There’s no polling involved — the listener reacts as soon as RabbitMQ pushes the message.

