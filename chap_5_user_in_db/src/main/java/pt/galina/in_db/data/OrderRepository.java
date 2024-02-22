package pt.galina.in_db.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.in_db.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
