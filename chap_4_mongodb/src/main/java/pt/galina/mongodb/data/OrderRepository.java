package pt.galina.mongodb.data;

import pt.galina.mongodb.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
