package pt.galina.rabbitmq_order_sender.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq_order_sender.entity.taco.TacoOrder;
import pt.galina.rabbitmq_order_sender.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
