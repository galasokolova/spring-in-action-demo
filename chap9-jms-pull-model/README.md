## Taco Cloud - Chapter 9: Sending messages with JMS
## *9.1: Pull Model*

### Steps to Run
###### ***Note!***
###### ***Ensure you have Docker installed and running.***
1. Navigate to the module directory:
    ```
    cd .\chap9-jms-pull-model
    ```
2.  Build and start the Docker containers:  
    ```bash
      docker-compose up --build
    ```

3. Navigate to http://localhost:8080 to start jms-sender.
4. Navigate to http://localhost:8081 to start jms-receiver.
5. Create an order and send it to the jms-receiver.
6. Put on the button "Receive Order" in order to pull the message.
7. Put on the button "Get Received Orders" to see the pulled message.
8. Then you can stop receiving messages with the button "Stop Receive"



