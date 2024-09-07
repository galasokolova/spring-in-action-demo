package pt.galina.cassandrareactivepersistence.entity.taco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.galina.cassandrareactivepersistence.entity.taco.Taco;
import pt.galina.cassandrareactivepersistence.entity.taco.data.TacoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TacoService {

    private final TacoRepository tacoRepository;

    @Autowired
    public TacoService(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    public Mono<Taco> save(Taco taco) {
        return tacoRepository.save(taco);
    }

    public Flux<Taco> findAll() {
        return tacoRepository.findAll();
    }

    public Mono<Taco> findById(String id) {
        return tacoRepository.findById(id);
    }

    public Mono<Void> deleteById(String id) {
        return tacoRepository.deleteById(id);
    }
}