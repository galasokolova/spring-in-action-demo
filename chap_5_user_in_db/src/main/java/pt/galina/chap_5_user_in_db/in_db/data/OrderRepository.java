package pt.galina.chap_5_user_in_db.in_db.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap_5_user_in_db.in_db.entity.taco.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
