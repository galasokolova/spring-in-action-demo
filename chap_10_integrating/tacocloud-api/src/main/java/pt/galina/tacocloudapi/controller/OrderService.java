package pt.galina.tacocloudapi.controller;

import org.springframework.stereotype.Service;
import pt.galina.tacocloudapi.taco.EmailOrder;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final List<EmailOrder> orders = new ArrayList<>();

    public void saveOrder(EmailOrder emailOrder) {
        orders.add(emailOrder);
    }

    public List<EmailOrder> getAllOrders() {
        return orders;
    }
}