package pt.galina.spring_webflux_demo.converter;

import org.springframework.stereotype.Component;
import pt.galina.spring_webflux_demo.dto.TacoOrderDTO;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.stream.Collectors;

@Component
public class TacoOrderDtoConverter {
    private final TacoDtoConverter tacoDtoConverter;

    public TacoOrderDtoConverter(TacoDtoConverter tacoDtoConverter) {
        this.tacoDtoConverter = tacoDtoConverter;
    }

    public TacoOrderDTO toDto(TacoOrder tacoOrder) {
        TacoOrderDTO dto = new TacoOrderDTO();
        dto.setId(tacoOrder.getId());
        dto.setPlacedAt(tacoOrder.getPlacedAt().toString());
        dto.setDeliveryName(tacoOrder.getDeliveryName());
        dto.setTacos(tacoOrder.getTacos().stream()
                .map(tacoDtoConverter::toDto)
                .collect(Collectors.toList()));
        dto.setUserId(tacoOrder.getUser().getId());

        return dto;
    }

    public Mono<TacoOrder> toEntity(TacoOrderDTO dto) {
        TacoOrder tacoOrder = new TacoOrder();
        tacoOrder.setId(dto.getId());
        tacoOrder.setPlacedAt(Date.valueOf(dto.getPlacedAt()));
        tacoOrder.setDeliveryName(dto.getDeliveryName());

        return Flux.fromIterable(dto.getTacos())
                .flatMap(tacoDtoConverter::toEntity)
                .collectList()
                .map(tacos -> {
                    tacoOrder.setTacos(tacos);
                    return tacoOrder;
                });
    }
}
