package pt.galina.clientreactive.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pt.galina.clientreactive.config.actuator.metrics.TacoCreatedEvent;
import pt.galina.clientreactive.entity.taco.Taco;
import pt.galina.clientreactive.entity.taco.data.TacoRepository;
import reactor.core.publisher.Mono;

@Component
public class TacoService {

    private final TacoRepository tacoRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TacoService(TacoRepository tacoRepository, ApplicationEventPublisher eventPublisher) {
        this.tacoRepository = tacoRepository;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Taco> save(Taco taco) {
        return tacoRepository.save(taco)
                .doOnSuccess(savedTaco -> eventPublisher.publishEvent(new TacoCreatedEvent(savedTaco)));
    }
}

