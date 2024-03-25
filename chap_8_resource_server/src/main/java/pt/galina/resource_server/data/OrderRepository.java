package pt.galina.resource_server.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.resource_server.entity.taco.TacoOrder;
import pt.galina.resource_server.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
