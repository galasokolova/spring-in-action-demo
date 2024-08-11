FROM openjdk:17

WORKDIR /app

COPY target/order-receiver-0.0.1-SNAPSHOT.jar order-receiver.jar

COPY ./scripts/entrypoint.sh /scripts/entrypoint.sh
COPY ./scripts/wait-for-it.sh /scripts/wait-for-it.sh

RUN chmod +x /scripts/entrypoint.sh
RUN chmod +x /scripts/wait-for-it.sh

ENTRYPOINT ["/scripts/entrypoint.sh"]
