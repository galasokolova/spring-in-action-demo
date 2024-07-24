## Taco Cloud - Chapter 6:
## 6.1 Fine-tuning autoconfiguration

This module demonstrates Spring Boot configurations for running an application using HTTPS, 
configuring access to the H2 console, and setting up an RSocket server.

### Steps to Configure HTTPS
#### 1. Creating a new keystore:
* Use the keytool command to generate a key pair (public and private keys) 
   and a self-signed certificate with the alias 'tomcat'.
##### Example command:
    keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -validity 365 -keystore mykeys.jks
#####
- The alias 'tomcat' is used as the identifier for the key.
- RSA represents the encryption algorithm for the key.
- The key size is set to 2048 bits.
- 'mykeys.jks' is the name of the keystore file.


#
#### 2. Adding a certificate to the keystore:
If you have an existing certificate, add it like this:

      keytool -importcert -alias mycert -file certificate.crt -keystore mykeys.jks
####
To create and manage a self-signed certificate using the JDK's keytool utility:

      keytool -genkeypair -alias mycert -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks

####
- 'mycert' is the alias for the new certificate.
- The certificate will also be valid for 365 days.
- 'keystore.jks' is the name of the keystore file to which the certificate will be added.

#
#### 3. Retrieving information about the keystore:
To view the list of keys in the keystore, use:

      keytool -list -keystore keystore.jks
####
- You will need to enter the keystore password to access its contents.

#
#### 4. Managing the keystore password:
To change the password of the keystore, use the following command:

    keytool -storepasswd -keystore mykeys.jks
####
- After executing the command, the system will prompt you to enter the old password, 
then the new password, and its confirmation.

#
#### 5. Configure application.yml.
#
#### 6. Configure Security in Spring Boot.
#
#### 7. Run the Application.
The application will be accessible at https://localhost:8443

### Accessing the H2 Console
https://localhost:8443/h2-console


