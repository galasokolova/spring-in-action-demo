## Taco Cloud - Chapter 8.1: 
This module demonstrates the implementation of security configurations using OAuth 2. 
It showcases how to protect API endpoints to ensure that only authorized users, 
specifically those with administrative privileges, 
can perform certain actions such as adding or deleting ingredients/orders.

### Build and run the project:
```bash
cd chap8-admin-preauthorize-demo
./mvnw spring-boot:run
```
### Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.

### Access for admin:
http://localhost:8080/adminReg register as an admin

### H2 database console:
* URL: http://localhost:8080/h2-console
* Username: sa
* Password: password

