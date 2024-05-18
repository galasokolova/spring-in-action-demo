package pt.galina.tacocloudapi.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.galina.tacocloudapi.taco.EmailOrder;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processEmailOrder(EmailOrder emailOrder) {
        // Логика обработки заказа из электронной почты
        sendOrderToMainApplication(emailOrder);
    }

    private void sendOrderToMainApplication(EmailOrder emailOrder) {
        String mainApplicationUrl = "http://localhost:8080/orders";
        restTemplate.postForObject(mainApplicationUrl, emailOrder, Void.class);
    }
}