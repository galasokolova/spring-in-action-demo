package pt.galina.client_app.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.client_app.entity.taco.TacoOrder;
import pt.galina.client_app.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
