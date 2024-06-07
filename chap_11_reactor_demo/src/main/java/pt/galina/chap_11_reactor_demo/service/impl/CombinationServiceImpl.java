package pt.galina.chap_11_reactor_demo.service.impl;

import org.springframework.stereotype.Service;
import pt.galina.chap_11_reactor_demo.service.CombinationService;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;

@Service
public class CombinationServiceImpl implements CombinationService {

    /**
     * Combines two streams: stream of characters and stream of food.
     *
     * @param characters List of characters.
     * @param foods List of food.
     * @param characterDelayMillis Delay in milliseconds between elements of characters stream.
     * @param foodDelayMillis Delay in milliseconds between elements of food stream.
     * @param foodSubscriptionDelayMillis Delay in milliseconds before subscription on food stream.
     * @return Combined Flux<String> stream of characters and food.
     */
    @Override
    public Flux<String> mergeFluxes(
            List<String> characters,
            List<String> foods,
            long characterDelayMillis,
            long foodDelayMillis,
            long foodSubscriptionDelayMillis) {
        Flux<String> characterFlux = Flux.fromIterable(characters)
                .delayElements(Duration.ofMillis(characterDelayMillis));

        Flux<String> foodFlux = Flux.fromIterable(foods)
                .delaySubscription(Duration.ofMillis(foodSubscriptionDelayMillis))
                .delayElements(Duration.ofMillis(foodDelayMillis));

        return characterFlux.mergeWith(foodFlux);
    }

    @Override
    public Flux <Tuple2<String, String>> zipFluxes(
            List<String> characters,
            List<String> foods) {

        Flux<String> characterFlux = Flux.fromIterable(characters);
        Flux<String> foodFlux = Flux.fromIterable(foods);

        return Flux.zip(characterFlux, foodFlux);
    }

    @Override
    public Flux <String>  zipFluxesToObject(
            List<String> characters,
            List<String> foods) {

        Flux<String> characterFlux = Flux.fromIterable(characters);
        Flux<String> foodFlux = Flux.fromIterable(foods);

        return Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
    }


    @Override
    public Flux<String> firstWithSignalFlux(
            List<String> slowList,
            List<String> fastList
    ) {
        Flux<String> slowFlux = Flux.fromIterable(slowList).delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.fromIterable(fastList);
        return Flux.firstWithSignal(slowFlux, fastFlux);
    }


}
