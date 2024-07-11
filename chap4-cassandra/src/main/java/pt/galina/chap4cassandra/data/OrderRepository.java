package pt.galina.chap4cassandra.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap4cassandra.entity.TacoOrder;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
}
