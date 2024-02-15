package pt.galina.chap_5.in_memory_user.data;

import pt.galina.chap_5.in_memory_user.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
