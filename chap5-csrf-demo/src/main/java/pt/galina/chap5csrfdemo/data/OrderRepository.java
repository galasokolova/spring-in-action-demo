package pt.galina.chap5csrfdemo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5csrfdemo.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
