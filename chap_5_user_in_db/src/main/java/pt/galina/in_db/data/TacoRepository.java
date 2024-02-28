package pt.galina.in_db.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.in_db.entity.taco.Taco;

public interface TacoRepository
         extends CrudRepository<Taco, Long> {

}
