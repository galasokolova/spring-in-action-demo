package pt.galina.chap7resttemplatedemo.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.chap7resttemplatedemo.entity.taco.TacoOrder;
import pt.galina.chap7resttemplatedemo.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
