package pt.galina.chap_11_reactor_demo.service.impl;

import org.junit.jupiter.api.Test;
import pt.galina.chap_11_reactor_demo.service.LogicService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class LogicServiceImplTest {

    private final LogicService logicService = new LogicServiceImpl();

    @Test
    void all() {

        Mono<Boolean> hasAMono = logicService.all("a", "aardvark", "elephant", "koala", "eagle", "kangaroo");
        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();

        Mono <Boolean> hasKMono = logicService.all("k", "aardvark", "elephant", "koala", "eagle", "kangaroo");
        StepVerifier.create(hasKMono)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void any(){
        Mono<Boolean> hasTMono = logicService.any("t", "aardvark", "elephant", "koala", "eagle", "kangaroo");
        StepVerifier.create(hasTMono)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> hasZMono = logicService.any("z", "aardvark", "elephant", "koala", "eagle", "kangaroo");
        StepVerifier.create(hasZMono)
                .expectNext(false)
                .verifyComplete();
    }



}