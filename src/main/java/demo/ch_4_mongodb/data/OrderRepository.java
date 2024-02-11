package demo.ch_4_mongodb.data;

import demo.ch_4_mongodb.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
