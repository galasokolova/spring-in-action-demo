package pt.galina.logging.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.logging.entity.taco.TacoOrder;
import pt.galina.logging.entity.user.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
