package pt.galina.finetunung_configuration.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.finetunung_configuration.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
