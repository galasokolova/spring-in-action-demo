package pt.galina.r2dbcpersistence.entity.taco.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.galina.r2dbcpersistence.entity.taco.Ingredient;
import pt.galina.r2dbcpersistence.entity.taco.Taco;
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import pt.galina.r2dbcpersistence.entity.taco.data.IngredientRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.OrderRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.TacoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {

    private final TacoRepository tacoRepo;
    private final OrderRepository orderRepo;
    private final IngredientRepository ingredientRepo;

    // Сохранение заказа
    public Mono<TacoOrder> save(TacoOrder tacoOrder) {
        return Mono.just(tacoOrder)
                .flatMap(order -> {
                    List<Taco> tacos = order.getTacos();
                    order.setTacos(new ArrayList<>());
                    return tacoRepo.saveAll(tacos)
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            })
                            .last();
                }).flatMap(orderRepo::save);
    }

    public Flux<Ingredient> findIngredientsBySlugs(List<String> slugs) {
        return Flux.fromIterable(slugs)
                .flatMap(ingredientRepo::findBySlug);
    }

    // Поиск заказа по ID
    public Mono<TacoOrder> findById(Long id) {
        return orderRepo.findById(id)
                .flatMap(order -> {
                    return tacoRepo.findAllById(order.getTacoIds())
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            })
                            .last();
                });
    }
}
