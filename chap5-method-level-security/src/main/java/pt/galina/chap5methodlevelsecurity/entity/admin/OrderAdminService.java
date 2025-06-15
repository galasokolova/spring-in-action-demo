package pt.galina.chap5methodlevelsecurity.entity.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pt.galina.chap5methodlevelsecurity.data.OrderRepository;

@Service
public class OrderAdminService {

    private final OrderRepository orderRepository;

    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders(){
        orderRepository.deleteAll();
    } // An example of requests only for admin's access
}
