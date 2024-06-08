package pt.galina.chap_11_reactor_demo.service;

import pt.galina.chap_11_reactor_demo.model.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface TransformationService {
    Flux<String> skipAFew(
            List<String> countList,
            long skippedNumber
    );

    Flux<String> skipAFewSeconds(
            List<String> countList,
            long startDelay,
            long skipPeriod
    );

    Flux<String> take(
            List<String> nationalParkList,
            long takenElements
    );

    Flux<String> takeForAwhile(
            List<String> nationalParkList,
            long startDelay,
            long takenPeriod
    );

    Flux<String> filter(List<String> nationalParkList);

    Flux<String> distinct(List<String> animals);

    Flux <Player> map(List<String> players);

    Flux <Player> flatMap(List<String> players);

    Flux <List<String>> buffer(
            List<String> fruit,
            int number);

    Flux<String> bufferAndFlatMap(
            int number,
            String... fruit);

    Mono<List<String>> collectList(String... fruit);

    Mono <Map<Character, String>> collectMap(String... animals);


}
