package pt.galina.chap3jpa.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap3jpa.entity.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
