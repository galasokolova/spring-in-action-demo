package pt.galina.rabbitmq.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.galina.rabbitmq.entity.taco.Taco;

import java.util.Optional;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

    Optional<Taco> findById(Long id);

    Taco save(Taco taco);
}
