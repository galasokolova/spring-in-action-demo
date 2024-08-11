#!/bin/bash

# Wait for RabbitMq
/scripts/wait-for-it.sh rabbitmq:5672 --timeout=60 --strict -- echo "▶️ RabbitMQ is up"

# Now start the order-sender
exec java -jar /app/order-sender.jar





