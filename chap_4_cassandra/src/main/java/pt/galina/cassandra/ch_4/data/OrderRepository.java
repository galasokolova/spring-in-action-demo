package pt.galina.cassandra.ch_4.data;

import pt.galina.cassandra.ch_4.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
}
