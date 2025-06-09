## 🌮 Taco Cloud – Chapter 2 "Developing web applications"

This is the second mini-project from the book *Spring in Action (6th Edition)*.  
It presents model data and form input in the browser.

### 🧠 What this chapter demonstrates:
- Building a Spring MVC web controller using *@Controller* and *@RequestMapping*

- Leveraging *@ModelAttribute* to populate model data before rendering views

- Maintaining user state across multiple requests with *@SessionAttributes*

- Handling form submission with *@PostMapping* and validating user input using *@Valid* and Errors

- Redirecting users and managing session completion via SessionStatus

- Using *@Slf4j* for clean and consistent logging

- Demonstrating separation of concerns between designing tacos and placing orders

> 🚫 **Note:** This module does **not use any database**. Database integration starts in later chapters.
><br>To keep things simple and database-free, the ingredient data is stored in a hardcoded List<Ingredient>. 
> <br>This allows us to focus on core Spring MVC features like controllers, model attributes, and form handling.


### 🛠 Technologies used:
- Spring Boot 3
- Spring MVC
- Thymeleaf


### 🚀 How to run the project:

#### Option 1: From terminal
```bash
   cd chap2
   ./mvnw spring-boot:run
```
#### Option 2: From IDE
You can also run the application by executing the main() method in the Chap2Application class:
```
src/main/java/pt/galina/chap1/Chap2Application.java
```


### 🌐 Access the application:
Open a web browser and navigate to http://localhost:8080 to access the running application.
<br> You’ll see the home page served by the HomeController.

#### 🚧 Navigation Note
Since authorization is not covered in Chapter 2, this project does not include navigation buttons from the home page to other sections.

To access the Taco Design page, you'll need to manually enter the following URL in your browser:

```
http://localhost:8080/design
```
Once you've selected your ingredients and named your taco, clicking "Submit Your Taco" will redirect you to the order form page.

After completing the order form and clicking "Submit" you'll be redirected back to the home page.


