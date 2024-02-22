package pt.galina.csrf.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.csrf.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
