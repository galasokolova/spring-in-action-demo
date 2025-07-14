# Taco Cloud — Chapter 9: Sending Messages Asynchronously
## Section 9.1 — Sending Messages with JMS (Push Model)

This module demonstrates how to implement asynchronous messaging using **JMS** with the **Push Model**,
as described in *Spring in Action*, Chapter 9.

It includes two submodules:

- `jms-order-sender` — A client web application that sends taco orders via JMS.
- `jms-order-receiver` — A server application that receives and processes the messages.

All components run in Docker containers to ensure isolated and consistent environments.

## 🧪 How it works

When you create a taco order in the `jms-order-sender` application and send it, the message is immediately pushed
to the message broker (ActiveMQ Artemis). The `jms-order-receiver` is already subscribed and automatically receives
the message as soon as it's published — no waiting or polling required.

Just click the "View Received Orders" button to see the incoming orders.

This demonstrates the Push Model of asynchronous messaging, 
where the server doesn't need to poll for new messages — it is notified the moment a message arrives.
## 🐳 Running the Application

> 💡 **Note:** Make sure Docker is installed and running on your machine.

### 1. Navigate to the project root:
```bash
cd ./chap9-jms-push-model
```

### 2. Build and launch the containers:
```bash
docker-compose up --build
```

### 3. Interact with the apps:
- Open [http://localhost:8080](http://localhost:8080) to use the `jms-order-sender` UI to place orders.
- Visit [http://localhost:8081](http://localhost:8081) to see received orders from the `jms-order-receiver`.

## 🧰 Technologies Used
- JMS (Jakarta Messaging)
- ActiveMQ Artemis
- Docker
