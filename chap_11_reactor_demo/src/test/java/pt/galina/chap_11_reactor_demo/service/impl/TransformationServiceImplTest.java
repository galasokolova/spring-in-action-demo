package pt.galina.chap_11_reactor_demo.service.impl;

import org.junit.jupiter.api.Test;
import pt.galina.chap_11_reactor_demo.model.Player;
import pt.galina.chap_11_reactor_demo.service.TransformationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class TransformationServiceImplTest {
    private final TransformationService transformationService = new TransformationServiceImpl();

    List<String> countList = List.of("one", "two", "skip a few", "ninety nine", "one hundred");
    List<String> nationalParkList = List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton");
    List<String> animalsList = List.of("dog", "cat", "bird", "dog", "bird", "anteater");
    List<String> playersList = List.of("Michael Jordan", "Scottie Pippen", "Steve Kerr");
    List<String> fruitList = List.of("apple", "orange", "banana", "kiwi", "strawberry");


    @Test
    void skipAFew() {
        Flux<String> countFlux = transformationService.skipAFew(countList, 3);
        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    void skipAFewSeconds() {
        // waiting 4 seconds and then starts publishing the elements
        Flux <String> countFlux = transformationService.skipAFewSeconds(countList, 1, 4);
        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")  // only the last 2 elements will be published
                .verifyComplete();
    }

    @Test
    void take(){
        Flux <String> nationalParkFlux = transformationService.take(nationalParkList, 3);
        StepVerifier.create (nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }


    @Test
    void takeForAwhile() {
        Flux <String> nationalParkFlux = transformationService.takeForAwhile(nationalParkList, 1, 3500);
        StepVerifier.create (nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    void filter() {
        Flux <String> nationalParkFlux = transformationService.filter(nationalParkList); // only values without spaces
        StepVerifier.create (nationalParkFlux)
                .expectNext ("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    void distinct() {
        Flux <String> animalFlux = transformationService.distinct(animalsList); //only unique values
        StepVerifier.create (animalFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    @Test
    void map() {
        Flux <Player> playerFlux = transformationService.map(playersList);
        StepVerifier.create (playerFlux)
                .expectNext(new Player ("Michael", "Jordan"))
                . expectNext (new Player ("Scottie", "Pippen"))
                . expectNext (new Player ("Steve", "Kerr"))
                .verifyComplete();
    }

    @Test
    void flatMap()  {
        Flux <Player> playerFlux = transformationService.flatMap(playersList);
        List< Player > playerList = Arrays.asList(    // creating a list of suggested names
                new Player ("Michael", "Jordan"),
                new Player ("Scottie", "Pippen"),
                new Player ("Steve", "Kerr")
        );
        StepVerifier.create (playerFlux)
                .expectNextMatches(playerList::contains) // checking if the first element is in the list
                . expectNextMatches (playerList::contains) // checking if the second element is in the list
                . expectNextMatches (playerList::contains) // checking if the third element is in the list
                .verifyComplete();    // checking if the operation is completed successfully
    }

    @Test
    void buffer() {
        Flux <List<String>> bufferedFlux = transformationService.buffer(fruitList, 3); // grouping by 3 elements
        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana")) // expecting the first list of 3 elements
                . expectNext (Arrays.asList("kiwi", "strawberry")) // expecting the second list of the last 2 elements
                .verifyComplete(); // checking if the operation is completed successfully
    }


    @Test
    void bufferAndFlatMap() throws Exception {
        VirtualTimeScheduler.getOrSet();

        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        Flux<String> bufferedFlux = transformationService.bufferAndFlatMap(
                3,
                        "apple", "orange", "banana", "kiwi", "strawberry")
                .log();

        StepVerifier.create(bufferedFlux)
                .expectNext("APPLE", "ORANGE", "BANANA")
                .expectNext("KIWI", "STRAWBERRY")
                .verifyComplete();
    }

    @Test
    void collectList(){
        Mono <List<String>> fruitListMono = transformationService.collectList("apple", "orange", "banana", "kiwi", "strawberry");
        StepVerifier
                .create(fruitListMono)
                .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    void collectMap() {
        Mono <Map<Character, String>> animalMapMono = transformationService.collectMap("aardvark", "elephant", "koala", "eagle", "kangaroo");
        StepVerifier
                .create(animalMapMono)
                .expectNextMatches(map -> map.size() == 3 &&
                        map.get('a').equals("aardvark") &&
                        map.get('e').equals("eagle") &&
                        map.get('k').equals("kangaroo")) .verifyComplete();
    }

}