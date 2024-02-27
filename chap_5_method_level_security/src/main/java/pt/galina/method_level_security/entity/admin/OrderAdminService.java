package pt.galina.method_level_security.entity.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pt.galina.method_level_security.data.OrderRepository;

@Service
public class OrderAdminService {

    private final OrderRepository orderRepository;

    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }
}
