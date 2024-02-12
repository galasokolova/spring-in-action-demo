package pt.galina.jpa.ch_3_jpa.data;

import pt.galina.jpa.ch_3_jpa.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
