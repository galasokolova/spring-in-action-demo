## Taco Cloud - Chapter 9: Sending messages
## *9.3: Kafka Push Model*

The module chap9-kafka-demo consists of two submodules:
* kafka-producer 
* kafka-listener

### Profiles
* *default*: Although configured, this profile is typically not used since the module is designed to run within Docker.
* *docker*: Used for building and running in Docker. Skips test execution.
#### To switch between profiles (if necessary), use the -P flag when running Maven, for example:
```bash
mvn clean install -Pdocker
```
or
```bash
mvn clean install -Pdefault
```

#### Running Tests
If you need to run tests, use the default profile. 
However, note that this profile is mainly for local testing and is not the typical use case for this module. 
To run tests, use:
```bash
mvn clean test -Pdefault
```
This will run all tests in the module using the default profile.


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

3. Navigate to http://localhost:8080 to start ´kafka-producer´. 
   Here you can create an order and send it to the Kitchen
4. Navigate to http://localhost:8081/receivedOrder to start ´kafka-listener´ 
   and see the received orders


*Each submodule (kafka-producer and kafka-listener) contains a scripts directory 
that includes essential scripts for launching 
and ensuring the proper functioning of the services*.

### About the Scripts Directory:
1. #### entrypoint.sh:
    This script serves as the entry point for the container. 
    It first checks if Kafka is available using the wait-for-it.sh script, 
    and only then starts the main application.
2. #### wait-for-it.sh:
    This script is designed to delay the start of the application until Kafka is up 
    and running. This is crucial to ensure that the application doesn’t start before the necessary 
    service is available.

