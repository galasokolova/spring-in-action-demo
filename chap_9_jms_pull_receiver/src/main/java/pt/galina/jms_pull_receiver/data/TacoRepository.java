package pt.galina.jms_pull_receiver.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.galina.jms_pull_receiver.entity.taco.Taco;

import java.util.Optional;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

    Optional<Taco> findById(Long id);

    Taco save(Taco taco);
}