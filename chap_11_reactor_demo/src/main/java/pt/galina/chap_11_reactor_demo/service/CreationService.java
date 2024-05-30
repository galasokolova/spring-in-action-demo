package pt.galina.chap_11_reactor_demo.service;


import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public interface CreationService {

    Flux <String>  createAFluxJust(String... strings);

    Flux<String> createAFluxFromArray(String[] array);

    Flux<String> createAFluxFromIterable(List<String> iterable);

    Flux<String> createAFluxFromStream(Stream<String> fruitStream);

    Flux <Integer> createAFluxRange(int start, int end);

    Flux<Long> createAFluxInterval(Long intervalDuration, int resultsQuantity);
}
