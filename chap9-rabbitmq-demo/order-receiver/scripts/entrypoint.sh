#!/bin/bash

# Wait for RabbitMq
/scripts/wait-for-it.sh rabbitmq:5672 --timeout=60 --strict -- echo "▶️ RabbitMQ is up"

# Now start the client application
exec java -jar /app/order-receiver.jar





