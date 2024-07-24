package pt.galina.chap6finetuningconfig.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap6finetuningconfig.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
