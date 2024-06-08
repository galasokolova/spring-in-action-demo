package pt.galina.chap_11_reactor_demo.service.impl;

import org.springframework.stereotype.Service;
import pt.galina.chap_11_reactor_demo.service.LogicService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LogicServiceImpl implements LogicService {

    @Override
    public Mono<Boolean> all(String letter, String... animals) {
        return Flux.just(animals)
                .all(a -> a.contains(letter));
    }


    @Override
    public Mono<Boolean> any(String letter, String... animals) {
        return Flux.just(animals)
                .any(a -> a.contains(letter));
    }
}
