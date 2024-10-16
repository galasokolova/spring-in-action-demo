package pt.galina.chap_18_googlecloud.service;

import org.springframework.stereotype.Component;
import pt.galina.chap_18_googlecloud.entity.taco.Taco;
import pt.galina.chap_18_googlecloud.entity.taco.data.TacoRepository;
import reactor.core.publisher.Mono;

@Component
public class TacoService {

    private final TacoRepository tacoRepository;

    public TacoService(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    public Mono<Taco> save(Taco taco) {
        return tacoRepository.save(taco);
    }
}

