## Taco Cloud - Chapter 9: Sending messages with JMS
## *9.1: Push Model*

### Steps to Run
###### ***Note!***
###### ***Ensure you have Docker installed and running.***
1. Navigate to the module directory:
    ```
    cd .\chap9-jms-push-model
    ```
2.  Build and start the Docker containers:  
    ```bash
      docker-compose up --build
    ```

3. Navigate to http://localhost:8080 to start jms-sender. 
   Here you can create an order and send it to the Kitchen
4. Navigate to http://localhost:8081/receivedOrder to start jms-receiver 
   and see the received orders




