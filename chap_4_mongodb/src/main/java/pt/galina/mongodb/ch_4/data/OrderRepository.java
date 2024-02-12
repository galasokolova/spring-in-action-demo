package pt.galina.mongodb.ch_4.data;

import pt.galina.mongodb.ch_4.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
