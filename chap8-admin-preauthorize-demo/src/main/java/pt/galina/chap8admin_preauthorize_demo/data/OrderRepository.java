package pt.galina.chap8admin_preauthorize_demo.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.taco.TacoOrder;
import pt.galina.chap8admin_preauthorize_demo.entity.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
