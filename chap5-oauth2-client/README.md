## 🌮 Taco Cloud – Chapter 5: Enabling Third-Party Authentication (5.3.3)

This module demonstrates **OAuth2 login with Google** as an alternative to the **Facebook login** example from section 5.3.3 in *Spring in Action*. It integrates Google OAuth2 to authenticate users securely and seamlessly.

---

### 🛠 Prerequisites: Set Up Google OAuth2

Before building and running the app, you’ll need to configure OAuth2 credentials in the **Google Cloud Console**.

---

### 🔐 Step-by-Step: Google OAuth2 Setup

#### ✅ Step 1: Create a Project in Google Cloud Console

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Click the project dropdown → **New Project**
3. Enter a project name (e.g., `Taco Cloud`)
4. Click **Create**

---

#### ✅ Step 2: Configure OAuth Consent Screen

1. In the left sidebar: **APIs & Services > OAuth consent screen**
2. Select **External** for user type and click **Create**
3. Fill in the required fields (e.g., App Name, User Support Email)
4. Add authorized domains (e.g., `localhost`)
5. Click **Save and Continue** (you can skip scopes and test users for localhost)

---

#### ✅ Step 3: Create OAuth 2.0 Credentials

1. Go to **APIs & Services > Credentials**
2. Click **Create Credentials → OAuth Client ID**
3. Choose **Web Application**
4. Configure URIs:

   * **Authorized JavaScript origins**: `http://localhost:8080`
   * **Authorized redirect URIs**: `http://localhost:8080/login/oauth2/code/google`
5. Click **Create**
6. Copy the generated **Client ID** and **Client Secret**

---

#### ✅ Step 4: Add OAuth2 Credentials to `application.yml`

Update your `application.yml` with the credentials:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: # your client-id
            client-secret: # your client-secret                                                                                        
```

---

### ▶️ Run the Application
#### Option 1: From terminal
```bash
cd chap5-oauth2-client

./mvnw spring-boot:run
```
#### Option 2: From IDE
Run the application by executing the `main()` method in:

```
src/main/java/pt/galina/chap5oauth2client/Chap5Oauth2ClientApplication.java
```

---

### 🌐 Access the App

* Open: [http://localhost:8080](http://localhost:8080)
* Click "Design a taco"
* Click the **“Login with Google”** button to authenticate

---

###  H2 Database Console

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* **Username**: `sa`
* **Password**: `password`

---



