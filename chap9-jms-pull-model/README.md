# Taco Cloud — Chapter 9: Sending Messages Asynchronously  
## Section 9.1 — Sending Messages with JMS (Pull Model)

This module demonstrates how to implement asynchronous messaging using **JMS** with the **Pull Model**, 
as described in *Spring in Action*, Chapter 9.

It includes two submodules:

- `jms-sender` — A client web application that sends taco orders via JMS.
- `jms-receiver` — A server application that receives and processes the messages.

All components run in Docker containers to ensure isolated and consistent environments.

---

## 🛠️ Prerequisites

- Docker installed and running
- Ports `8080` (sender) and `8081` (receiver) available

---

## 🚀 Running the Application

1. **Navigate to the project directory:**
   ```bash
   cd ./chap9-jms-pull-model
   ```

2. **Build and start the containers:**
   ```bash
   docker-compose up --build
   ```

3. **Open the applications in your browser:**
   - Sender: [http://localhost:8080](http://localhost:8080)
   - Receiver: [http://localhost:8081](http://localhost:8081)

---

## 🧪 How it works:

1. **In the sender app**, create a taco order and click **"Send Order"**.  
   This sends the order to the JMS queue.

2. **In the receiver app**, click **"Receive Order"** to manually pull the next message from the queue.

3. Click **"Get Received Orders"** to view all received orders.

4. Use **"Stop Receive"** to stop pulling new messages.

---

## 📝 Notes

- The **pull model** means the receiver explicitly requests messages from the queue, rather than being notified automatically.
- This demo uses a standalone ActiveMQ Artemis broker running in a Docker container. 
  It enables asynchronous communication between the sender and receiver applications using the JMS pull model.

---

Feel free to explore the message structure and customize how orders are serialized or deserialized using Jackson.