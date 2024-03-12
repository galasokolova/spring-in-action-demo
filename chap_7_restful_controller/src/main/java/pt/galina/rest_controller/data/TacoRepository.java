package pt.galina.rest_controller.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import pt.galina.rest_controller.entity.taco.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}
