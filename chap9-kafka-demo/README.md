# Taco Cloud — Chapter 9: Sending Messages Asynchronously
## *9.3: Kafka Push Model*

This module demonstrates how to implement asynchronous messaging using **Apache Kafka** with the **Push Model**, 
as described in *Spring in Action*, Chapter 9.

The `chap9-kafka-demo` project consists of two Spring Boot microservices:

- `kafka-producer` — A client web application that sends taco orders via Kafka.
- `kafka-listener` — A backend service that listens to Kafka and receives taco orders in real time.

All services are containerized with Docker, and startup coordination is handled using the `wait-for-it.sh` 
script to ensure dependencies (like Kafka) are ready before each application starts.

---
### 🧾 Scripts Overview:
1. #### /scripts/entrypoint.sh 
   *  Ensures Kafka is available before starting the Spring Boot app.
2. #### /scripts/wait-for-it.sh
   * A lightweight shell script to wait for a host/port to become available.  
      Each app uses this logic to prevent premature startup failures.

---
## ⚙️ How It Works — Container Logic

The `docker-compose.yml` defines the full system architecture:

1. **Zookeeper** starts first. Kafka depends on it for coordination.
2. **Kafka** waits for Zookeeper and then initializes. It's exposed on port `9092`.
3. **kafka-producer** and **kafka-listener** both:
    - Wait for Kafka to be reachable (using `wait-for-it.sh` in `entrypoint.sh`)
    - Then launch their respective applications.

Each app uses a profile named `docker`, which skips tests during image build.

---
### Building and Running the Application
***Note!***
***Ensure you have Docker installed and running.***

1. Navigate to the module directory:
    ```
    cd .\chap9-kafka-demo
    ```
2. Build and start the Docker containers:
    ```bash
      docker-compose up --build
    ```

## 🧪 Messaging Logic (Kafka Push Model)

Once everything is running:

1. Go to [http://localhost:8080](http://localhost:8080) to access `kafka-producer`.
2. Submit a taco order via the form and click "Send Order."
3. The app pushes the message to Kafka instantly.
4. The `kafka-listener` (already subscribed) receives the message and stores it in memory.
5. Go to [http://localhost:8081](http://localhost:8081) and click **"View Received Orders"** to see them.

There’s no polling involved — the listener reacts as soon as Kafka emits a message. 

---

### 🧰 Profiles
* *default*: For local testing (not used inside Docker).
* *docker*: For building Docker images without running tests.

#### You can switch profiles with Maven. For example:
```bash
  mvn clean install -Pdocker
```

#### Running Tests
If you need to run tests, use the default profile.
However, note that this profile is mainly for local testing and is not the typical use case for this module.
To run tests locally:
```bash
  mvn clean test -Pdefault
```
This will run all tests in the module using the default profile.

