package pt.galina.rabbitmq.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq.entity.taco.TacoOrder;
import pt.galina.rabbitmq.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
