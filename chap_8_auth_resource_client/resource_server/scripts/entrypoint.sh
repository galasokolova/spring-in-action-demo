#!/bin/bash

# wait-for-it.sh is used to check for authorization_server readiness
/scripts/wait-for-it.sh authorization_server:9000 --timeout=60 --strict -- echo "Authorization Server is up"

# Now start the resource server application
exec java -jar /app/resource_server.jar




