package pt.galina.chap_11_reactor_demo.service.impl;

import org.springframework.stereotype.Service;
import pt.galina.chap_11_reactor_demo.service.CreationService;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CreationServiceImpl implements CreationService {

    @Override
    public Flux<String> createAFluxJust(String... strings) {
        return Flux.just(strings);
    }

    @Override
    public Flux<String> createAFluxFromArray(String[] array) {
        return Flux.fromArray(array);
    }

    @Override
    public Flux<String> createAFluxFromIterable(List<String> iterable) {
        return Flux.fromIterable(iterable);
    }

    @Override
    public Flux<String> createAFluxFromStream(Stream<String> fruitStream) {
        return Flux.fromStream(fruitStream);
    }

    @Override
    public Flux<Integer> createAFluxRange(int start, int end) {
        return Flux.range(start, end);
    }

    @Override
    public Flux<Long> createAFluxInterval(Long intervalDuration, int resultsQuantity) {
        return Flux. interval(Duration.ofSeconds(intervalDuration)) // interval() doesn´t have max value, so it can work forever, that´s why
                .take(resultsQuantity); //  take() method restricts the quantity of first elements

    }

}
