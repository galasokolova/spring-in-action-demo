FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app

# Copy POM files to download dependencies
COPY ../../pom.xml /app/pom.xml
COPY ./pom.xml /app/kafka-listener/pom.xml

# Copy the entire project to the container
COPY ../../ /app

# Download dependencies for offline build
RUN mvn dependency:go-offline -B -f kafka-listener/pom.xml

# Build the application using the Docker profile
RUN mvn clean package -Pdocker -f kafka-listener/pom.xml

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/kafka-listener/target/kafka-listener-0.0.1-SNAPSHOT.jar /app/kafka-listener.jar

COPY ./kafka-listener/scripts/entrypoint.sh /scripts/entrypoint.sh
COPY ./kafka-listener/scripts/wait-for-it.sh /scripts/wait-for-it.sh

RUN chmod +x /scripts/entrypoint.sh /scripts/wait-for-it.sh

ENTRYPOINT ["/scripts/entrypoint.sh"]
