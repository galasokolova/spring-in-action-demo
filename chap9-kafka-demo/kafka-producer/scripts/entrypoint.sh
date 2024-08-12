#!/bin/bash

# Wait for Kafka
/scripts/wait-for-it.sh kafka:9092 --timeout=60 --strict -- echo "Kafka Container is up"

# Now start the kafka-producer application
exec java -jar /app/kafka-producer.jar





