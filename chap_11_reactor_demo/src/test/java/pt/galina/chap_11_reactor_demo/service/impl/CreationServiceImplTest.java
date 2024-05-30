package pt.galina.chap_11_reactor_demo.service.impl;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Stream;

class CreationServiceImplTest {
    private final CreationServiceImpl creationService = new CreationServiceImpl();

    @Test
    void createAFluxJust_shouldEmitAllElementsAndComplete() {
        Flux <String> fruitFlux = creationService.createAFluxJust("Apple", "Orange", "Grape", "Banana", "Strawberry");

        StepVerifier.create(fruitFlux)  // Here StepVerifier subscribes on Flux
                .expectNext("Apple")   // checks if the element of the stream is equal to "Apple"
                .expectNext ("Orange")
                .expectNext ("Grape")
                .expectNext ("Banana")
                .expectNext ("Strawberry")
                .expectComplete() // Checks if Flux has finished successfully
                .verify();
    }


    @Test
    void createAFluxFromArray() {
        String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux<String> fruitFlux = creationService.createAFluxFromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

   @Test
   void createAFluxFromList() {
       List<String> fruitList = List.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
       Flux<String> fruitFlux = creationService.createAFluxFromIterable(fruitList);

       StepVerifier.create(fruitFlux)
               .expectNext("Apple")
               .expectNext("Orange")
               .expectNext("Grape")
               .expectNext("Banana")
               .expectNext("Strawberry")
               .verifyComplete();
   }

    @Test
    void createAFluxFromStream() {
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux <String> fruitFlux = creationService.createAFluxFromStream(fruitStream);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext ("Orange")
                .expectNext ("Grape")
                .expectNext ("Banana")
                .expectNext ("Strawberry")
                .verifyComplete();
    }

    @Test      //  тест демонстрирует, как из Flux создать счетчик
    void createAFlux_range() {
        Flux <Integer> intervalFlux = creationService.createAFluxRange(1, 5);   // создается счетчик Flux, генерирующий значения от 1 до 5
        StepVerifier.create(intervalFlux)   //  StepVerifier доказывает, что он действительно опубликует пять элементов – целые числа от 1 до 5
                .expectNext (1)
                .expectNext (2)
                .expectNext (3)
                .expectNext (4)
                .expectNext (5)
                .verifyComplete();
    }

    @Test
    void createAFlux_interval() {
        Flux <Long> intervalFlux = creationService.createAFluxInterval(1L, 5);

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext (1L)
                .expectNext (2L)
                .expectNext (3L)
                .expectNext (4L)
                .verifyComplete();
    }




}