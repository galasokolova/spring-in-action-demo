#!/bin/bash

# Wait for both Kafka Container
/scripts/wait-for-it.sh kafka:9092 --timeout=60 --strict -- echo "Kafka Container is up"

# Now start the kafka-listener application
exec java -jar /app/kafka-listener.jar





