#!/bin/bash

# Wait for both Kafka Container
/scripts/wait-for-it.sh kafka:9092 --timeout=60 --strict -- echo "Kafka Container is up"

# Now start the kafka_listener application
exec java -jar /app/kafka_listener.jar





