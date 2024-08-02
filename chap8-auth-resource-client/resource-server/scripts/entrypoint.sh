#!/bin/bash

# wait-for-it.sh is used to check for auth-server readiness
/scripts/wait-for-it.sh authserver:9000 --timeout=60 --strict -- echo "Authorization Server is up"

# Now start the resource server application
exec java -jar /app/resource-server.jar




