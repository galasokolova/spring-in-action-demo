## Taco Cloud - Chapter 8:  
## *8.3: Securing an API with a resource server*
The chap8-resource-server module demonstrates the configuration of a resource server.
It can be run as part of the full application setup.

### Steps to Run
1. Start the Authorization Server:
Navigate to the chap8-authorization-server directory.
Run the server using your preferred method (e.g., using an IDE or command line).
The server typically starts on http://localhost:9000.

2. Start the Resource Server:
Navigate to the chap8-resource-server directory.
Ensure the authorization server is running before starting the resource server.
Run the server, which usually starts on http://localhost:8080.

3. Start the Client Application:
Navigate to the chap8-client-app directory.
Ensure both the authorization server and resource server are running.
Run the client application, which starts on http://localhost:9090.

### You can explore its functionality using *curl* requests.
1. Authorize with OAuth2: open your browser and navigate to:
   ```
   http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients
   ```
2. Follow the authorization steps to obtain an Authorization Code
3. Request for token:
   ```
   $ curl localhost:9000/oauth2/token \
   -H"Content-type: application/x-www-form-urlencoded" \
   -d"grant_type=authorization_code" \
   -d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client" \
   -d"code=your_code_here" \
   -u taco-admin-client:secret

   ```
4. For example, you can perform the following operations: 
   ```
   curl -X GET http://localhost:8080/api/ingredients \
   -H "Authorization: Bearer your_access_token"
   ```
   ```
   $ curl http://localhost:8080/api/ingredients \
   -H "Content-type: application/json" \
   -d "{\"id\":\"SHMP\", \"name\":\"Coconut Shrimp\", \"type\":\"PROTEIN\"}" \
   -H "Authorization: Bearer "
   ```
   ```
   curl localhost:9000/oauth2/token \
   -H "Content-type: application/x-www-form-urlencoded" \
   -d "grant_type=refresh_token&refresh_token=<refresh_token>" \
   -u taco-admin-client:secret
   ```


### Access the application:
Once the containers are up and running, open your browser and go to http://localhost:9090. 
Follow the on-screen instructions to interact with the application. 

###### username: habuma
###### password: password
or
###### username: tacochef
###### password: password
