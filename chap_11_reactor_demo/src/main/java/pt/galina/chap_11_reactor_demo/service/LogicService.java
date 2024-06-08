package pt.galina.chap_11_reactor_demo.service;

import reactor.core.publisher.Mono;

public interface LogicService {
    Mono<Boolean> all(
            String letter,
            String... animals
    );

    Mono<Boolean> any(
            String letter,
            String... animals
    );
}
