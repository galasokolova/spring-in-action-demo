package pt.galina.jms_sender.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.galina.jms_sender.entity.taco.Taco;

import java.util.Optional;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

    Optional<Taco> findById(Long id);

    Taco save(Taco taco);
}
