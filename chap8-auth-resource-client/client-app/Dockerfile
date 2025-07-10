FROM openjdk:17

COPY target/client-app-0.0.1-SNAPSHOT.jar /app/client-app.jar

COPY scripts/entrypoint.sh /scripts/entrypoint.sh
COPY scripts/wait-for-it.sh /scripts/wait-for-it.sh

RUN dos2unix /scripts/*.sh


ENTRYPOINT ["/scripts/entrypoint.sh"]
