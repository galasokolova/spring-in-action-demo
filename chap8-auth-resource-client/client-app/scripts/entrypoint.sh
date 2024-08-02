#!/bin/bash

# Wait for both auth-server and resource-server
/scripts/wait-for-it.sh authserver:9000 --timeout=60 --strict -- echo "Authorization Server is up"
/scripts/wait-for-it.sh resource-server:8080 --timeout=60 --strict -- echo "Resource Server is up"

# Now start the client application
exec java -jar /app/client-app.jar





