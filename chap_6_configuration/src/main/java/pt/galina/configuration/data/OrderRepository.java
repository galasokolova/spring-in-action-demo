package pt.galina.configuration.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.configuration.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
