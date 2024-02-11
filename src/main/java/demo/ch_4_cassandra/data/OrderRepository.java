package demo.ch_4_cassandra.data;

import demo.ch_4_cassandra.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
}
