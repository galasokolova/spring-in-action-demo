package pt.galina.chap_11_reactor_demo.service.impl;

import org.springframework.stereotype.Service;
import pt.galina.chap_11_reactor_demo.model.Player;
import pt.galina.chap_11_reactor_demo.service.TransformationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class TransformationServiceImpl implements TransformationService {

    @Override
    public Flux<String> skipAFew(
            List<String> countList,
            long skippedNumber) {
        return Flux
                .fromIterable(countList)
                .skip(skippedNumber);
    }

    @Override
    public Flux<String> skipAFewSeconds(
            List<String> countList,
            long countListDelay,
            long skipPeriod) {
        return Flux.fromIterable(countList)
                .delayElements(Duration.ofSeconds(countListDelay))
                .skip(Duration.ofSeconds(skipPeriod));
    }

    @Override
    public Flux<String> take(
            List<String> nationalParkList,
            long takenElements
            ) {
        return Flux.fromIterable(nationalParkList)
                .take(takenElements);
    }

    @Override
    public Flux<String> takeForAwhile(
            List<String> nationalParkList,
            long startDelay,
            long takenPeriod
    ) {
        return Flux.fromIterable(nationalParkList)
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(takenPeriod));
    }


    @Override
    public Flux<String> filter(List<String> nationalParkList) {
        return Flux.fromIterable(nationalParkList)
                .filter(nationalPark -> !nationalPark.contains(" "));
    }


    @Override
    public Flux<String> distinct(List<String> animals) {
        return Flux.fromIterable(animals)
                .distinct();
    }


    @Override
    public Flux<Player> map(List<String> players) {
        return Flux.fromIterable(players)
                .map(
                        n -> {
                            String[] split = n.split("\\s"); //splits lines on spaces
                            return new Player (split[0], split[1]); //creates objects Player for example: ["Michael", "Jordan"]

                        });
    }


    @Override
    public Flux<Player> flatMap(List<String> players) {
        return Flux.fromIterable(players)
                . flatMap( n  ->  Mono.just(n)  // transforming every element from Flux to Mono<String> (1 element)
                        . map(p -> {  // applying map() to every element of Mono<String>
                            String[] split = p.split("\\s");   // splitting into name and surname
                            return new Player (split [0], split [1]); // creating object Player and return it
                        })
                        . subscribeOn(Schedulers.parallel())   // producing the operation in parallel stream
                );

    }


    @Override
    public Flux<List<String>> buffer(
            List<String> fruit,
            int number) {
        return Flux.fromIterable(fruit)
                .buffer(number);
    }


    @Override
    public Flux<String> bufferAndFlatMap(
            int number,
            String... fruit) {
        return Flux.just(fruit)
                . buffer(number)
                . flatMap(x ->
                        Flux.fromIterable(x)
                                . map(String::toUpperCase)
                                . subscribeOn(Schedulers.parallel())
                );
    }


    @Override
    public Mono<List<String>> collectList(String... fruit) {
        return Flux.just(fruit)
                .collectList();
    }


    @Override
    public Mono<Map<Character, String>> collectMap(String... animals) {
        return Flux.just(animals)
                .collectMap(a -> a.charAt(0));
    }

}
