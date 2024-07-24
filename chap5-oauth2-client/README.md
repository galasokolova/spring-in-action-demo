## Taco Cloud - Chapter 5: 5.3.3 Enabling third-party authentication

This module differs from section 5.3.3 of the book "Spring in Action" 
where the example uses Facebook login. 
In this module, login via Google has been implemented .
###
### Setting Up Google OAuth2 for Your Application
Before build and run the module, follow these steps to configure Google OAuth2:
###
#### Step 1: Create a Project in Google Cloud Console:
1. Navigate to Google Cloud Console 
   * https://console.cloud.google.com/
2. Create a New Project:
   * Click on the project dropdown at the top of the page.
   * Click "New Project".
   * Enter a project name (e.g., Taco Cloud).
   * Click "Create".

#### Step 2: Configure OAuth Consent Screen

1. Navigate to OAuth Consent Screen:
   * In the left sidebar, go to APIs & Services > OAuth consent screen.
2. Set Up Consent Screen:
   * Select "External" for user type and click "Create".
   * Fill in the required fields (App name, User support email, etc.).
   * Add your application’s domain and authorized domains.
   * Save the changes.
#### Step 3: Create OAuth 2.0 Credentials
1. Go to APIs & Services > Credentials.
2. Create Credentials:
   * Click "Create Credentials" and select "OAuth 2.0 Client IDs".
   * Select "Web application" as the application type.
3. Set Authorized URIs:
   * Under "Authorized JavaScript origins", add: http://localhost:8080.
   * Under "Authorized redirect URIs", add: http://localhost:8080/login/oauth2/code/google.
4. Generate Credentials:
   * Click "Create". 
   * Copy the Client ID and Client Secret displayed on the screen.
5. Add your Google OAuth2 credentials to the application.yml file

### Build and run the project:
```bash
cd chap5-oauth2-client
./mvnw spring-boot:run
```

### Access the application:
* Open a web browser and navigate to http://localhost:8080 to access the running application.
* Click the "Login with Google" link to initiate the OAuth2 flow.


### H2 database console:
* URL: http://localhost:8080/h2-console
* Username: sa
* Password: password

