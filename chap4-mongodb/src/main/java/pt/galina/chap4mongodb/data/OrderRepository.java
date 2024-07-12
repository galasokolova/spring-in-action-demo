package pt.galina.chap4mongodb.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap4mongodb.entity.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
