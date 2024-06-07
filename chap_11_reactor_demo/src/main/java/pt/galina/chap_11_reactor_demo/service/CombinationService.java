package pt.galina.chap_11_reactor_demo.service;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.List;

public interface CombinationService {
    Flux<String> mergeFluxes(
            List<String> characters,
            List<String> foods,
            long characterDelayMillis,
            long foodDelayMillis,
            long foodSubscriptionDelayMillis
    );

    Flux <Tuple2<String, String>> zipFluxes(
            List<String> characters,
            List<String> foods
    );

    Flux <String>  zipFluxesToObject(
            List<String> characters,
            List<String> foods
    );

    Flux <String> firstWithSignalFlux(
            List<String> slowList,
            List<String> fastList
    );
}
