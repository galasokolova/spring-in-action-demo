package pt.galina.chap_15_actuatordemo.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pt.galina.chap_15_actuatordemo.config.actuator.metrics.TacoCreatedEvent;
import pt.galina.chap_15_actuatordemo.entity.taco.Taco;
import pt.galina.chap_15_actuatordemo.entity.taco.data.TacoRepository;
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

