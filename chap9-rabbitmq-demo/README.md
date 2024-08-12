## Taco Cloud - Chapter 9: Sending messages
## *9.2: RabbitMQ Push Model*

### Steps to Run
###### ***Note!***
###### ***Ensure you have Docker installed and running.***
1. Navigate to the module directory:
    ```
    cd .\chap9-rabitmq-demo
    ```
2.  Build and start the Docker containers:  
    ```bash
      docker-compose up --build
    ```

3. Navigate to http://localhost:8080 to start order-sender. 
   Here you can create an order and send it to the Kitchen
4. Navigate to http://localhost:8081/receivedOrder to start order-receiver 
   and see the received orders


*Each submodule (order-sender and order-receiver) contains a scripts directory that includes essential scripts for launching and ensuring the proper functioning of the services*.

### What’s inside the scripts directory and its purpose:
1. ### entrypoint.sh:
    This script serves as the entry point for the container. It first checks if RabbitMQ is available using the wait-for-it.sh script, and only then starts the main application.
2. ### wait-for-it.sh:
    This script is designed to delay the start of the application until RabbitMQ is up and running. This is crucial to ensure that the application doesn’t start before the necessary service is available.


