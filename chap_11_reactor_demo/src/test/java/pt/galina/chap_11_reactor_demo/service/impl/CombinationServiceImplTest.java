package pt.galina.chap_11_reactor_demo.service.impl;

import org.junit.jupiter.api.Test;
import pt.galina.chap_11_reactor_demo.service.CombinationService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;


class CombinationServiceImplTest {
    private final CombinationService combinationService = new CombinationServiceImpl();

    @Test
    void mergeFluxes() {
        VirtualTimeScheduler.getOrSet(); //  VirtualTimeScheduler to handle time in the test

        Flux<String> mergedFlux = combinationService.mergeFluxes(
                List.of("Garfield", "Kojak", "Barbossa"),
                List.of("Lasagna", "Lollipops", "Apples"),
                500L,
                250L,
                500L
        );

        StepVerifier.withVirtualTime(() -> mergedFlux)
                .expectSubscription()
                .thenAwait(Duration.ofMillis(500))
                .expectNext("Garfield")
                .thenAwait(Duration.ofMillis(250))
                .expectNext("Lasagna")
                .thenAwait(Duration.ofMillis(500))
                .expectNext("Kojak")
                .thenAwait(Duration.ofMillis(500))
                .expectNext("Lollipops")
                .thenAwait(Duration.ofMillis(500))
                .expectNext("Apples")
                .thenAwait(Duration.ofMillis(500))
                .expectNext("Barbossa")
                .verifyComplete();
    }

    @Test
    void zipFluxes() {
        List <String> characterFlux = List.of("Garfield", "Kojak", "Barbossa");
        List <String> foodFlux = List.of("Lasagna", "Lollipops", "Apples");

        Flux <Tuple2<String, String>> zippedFlux = combinationService.zipFluxes(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNext(Tuples.of("Garfield", "Lasagna"))
                .expectNext(Tuples.of("Kojak", "Lollipops"))
                .expectNext(Tuples.of("Barbossa", "Apples"))
                .verifyComplete();
    }

    @Test
    void zipFluxesToObject() {
        List <String> characterFlux = List.of("Garfield", "Kojak", "Barbossa");
        List <String> foodFlux = List.of("Lasagna", "Lollipops", "Apples");

        Flux <String> zippedFlux =  combinationService.zipFluxesToObject(characterFlux, foodFlux);
        StepVerifier.create(zippedFlux)
                .expectNext("Garfield eats Lasagna")
                . expectNext ("Kojak eats Lollipops")
                . expectNext ("Barbossa eats Apples")
                .verifyComplete();
    }

    @Test
    void firstWithSignalFlux() {
        Flux <String> slowFlux = Flux
                .just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100)); //slowFlux will publish the 1st element with delay 100ms after subscription
        Flux <String> fastFlux = Flux.just("hare", "cheetah", "squirrel"); //new Flux will ignore it and publish only fastFlux

        Flux <String> firstFlux = Flux. firstWithSignal(slowFlux, fastFlux) ;
        StepVerifier.create(firstFlux)
                .expectNext("hare")
                . expectNext ("cheetah")
                . expectNext ("squirrel")
                .verifyComplete();
    }


}