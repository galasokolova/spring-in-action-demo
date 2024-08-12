## Taco Cloud - Chapter 9: Sending messages
## *9.3: Kafka Push Model*

The module chap9-kafka-demo consists of two submodules:
* kafka-producer 
* kafka-listener

### Profiles
* *default*: Used for local development. 
  Executes all tests during the build process.
* *docker*: Used for building and running in Docker. Skips test execution.
#### To switch between profiles, use the -P flag when running Maven, for example:
```bash
mvn clean install -Pdocker
```
or
```bash
mvn clean install -Pdefault
```

### Steps to Run
###### ***Note!***
###### ***Ensure you have Docker installed and running.***
1. Navigate to the module directory:
    ```
    cd .\chap9-kafka-demo
    ```
2. Build and start the Docker containers:  
    ```bash
      docker-compose up --build
    ```

3. Navigate to http://localhost:8080 to start ´kafka-producer´. 
   Here you can create an order and send it to the Kitchen
4. Navigate to http://localhost:8081/receivedOrder to start ´kafka-listener´ 
   and see the received orders


*Each submodule (kafka-producer and kafka-listener) contains a scripts directory 
that includes essential scripts for launching 
and ensuring the proper functioning of the services*.

### What’s inside the scripts directory and its purpose:
1. ### entrypoint.sh:
    This script serves as the entry point for the container. 
    It first checks if Kafka is available using the wait-for-it.sh script, 
    and only then starts the main application.
2. ### wait-for-it.sh:
    This script is designed to delay the start of the application until Kafka is up 
    and running. This is crucial to ensure that the application doesn’t start before the necessary 
    service is available.

