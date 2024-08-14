## Taco Cloud - Chapter 10: Integrating Spring

The module chap_10_integrating consists of 2 submodules:
* tacocloud-api
* tacocloud-email-handler

This module integrates a mail server with a REST API to process taco orders received via email.


### Steps to Run

1. Navigate to the module directory:
    ```
    cd .\chap9-jms-push-model
    ```
2. Configure the file with your credentials and settings.
3. Run both submodules: tacocloud-api and tacocloud-email-handler.
4. Navigate to http://localhost:8080/api/orders 
5. Send an email to the address specified in the application.yml 
   with the subject "taco order" and a list of tacos in the body. 
   For example:
    ```
    TacoOne: FLTO, TOMATOES, CHEDDAR, SALSA
    TacoTwo: COTO, LETTUCE, GRBF, SRCR
    TacoThree: FLTO, CARN, TMTO, SLSA
    ```
6. The web page updates every 5 seconds. When you reload the page, 
   the order sent to the email address specified in application.yml will appear. 
   The email will be automatically marked as "read" once the email-handler processes it 
   and sends it to tacocloud-api.
