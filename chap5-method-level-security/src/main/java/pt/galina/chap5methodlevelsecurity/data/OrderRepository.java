package pt.galina.chap5methodlevelsecurity.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5methodlevelsecurity.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
