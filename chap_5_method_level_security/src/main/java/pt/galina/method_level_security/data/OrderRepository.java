package pt.galina.method_level_security.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.method_level_security.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
