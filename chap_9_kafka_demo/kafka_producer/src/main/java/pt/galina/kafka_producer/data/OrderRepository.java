package pt.galina.kafka_producer.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.kafka_producer.entity.taco.TacoOrder;
import pt.galina.kafka_producer.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
