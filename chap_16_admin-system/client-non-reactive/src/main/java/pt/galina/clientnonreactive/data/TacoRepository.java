package pt.galina.clientnonreactive.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.clientnonreactive.entity.taco.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
