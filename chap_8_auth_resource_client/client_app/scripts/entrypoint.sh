#!/bin/bash

# Wait for both authorization_server and resource_server
/scripts/wait-for-it.sh authorization_server:9000 --timeout=60 --strict -- echo "Authorization Server is up"
/scripts/wait-for-it.sh resource_server:8080 --timeout=60 --strict -- echo "Resource Server is up"

# Now start the client application
exec java -jar /app/client_app.jar





