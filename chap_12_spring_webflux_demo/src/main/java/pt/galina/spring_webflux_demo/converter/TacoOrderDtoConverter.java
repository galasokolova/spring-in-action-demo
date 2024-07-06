//package pt.galina.spring_webflux_demo.converter;
//
//import org.springframework.stereotype.Component;
//import pt.galina.spring_webflux_demo.dto.TacoOrderDTO;
//import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@Component
//public class TacoOrderDtoConverter {
//    private final TacoDtoConverter tacoDtoConverter;
//    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
//
//    public TacoOrderDtoConverter(TacoDtoConverter tacoDtoConverter) {
//        this.tacoDtoConverter = tacoDtoConverter;
//    }
//
//    public TacoOrderDTO toDto(TacoOrder tacoOrder) {
//        TacoOrderDTO dto = new TacoOrderDTO();
//        dto.setId(tacoOrder.getId());
//        if (tacoOrder.getPlacedAt() != null) {
//            dto.setPlacedAt(tacoOrder.getPlacedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter));
//        }
//        dto.setDeliveryName(tacoOrder.getDeliveryName());
//        dto.setTacos(tacoOrder.getTacos().stream()
//                .map(tacoDtoConverter::toDto)
//                .collect(Collectors.toList()));
//        if (tacoOrder.getUser() != null) {
//            dto.setUserId(tacoOrder.getUser().getId());
//        }
//
//        return dto;
//    }
//
//    public Mono<TacoOrder> toEntity(TacoOrderDTO dto) {
//        TacoOrder tacoOrder = new TacoOrder();
//        tacoOrder.setId(dto.getId());
//        if (dto.getPlacedAt() != null) {
//            LocalDate localDate = LocalDate.parse(dto.getPlacedAt(), formatter);
//            tacoOrder.setPlacedAt(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        }
//        tacoOrder.setDeliveryName(dto.getDeliveryName());
//        tacoOrder.setDeliveryStreet("Default Street");
//        tacoOrder.setDeliveryCity("Default City");
//        tacoOrder.setDeliveryState("Default State");
//        tacoOrder.setDeliveryZip("00000");
//        tacoOrder.setCcNumber("0000000000000000");
//        tacoOrder.setCcExpiration("01/70");
//        tacoOrder.setCcCVV("000");
//
//        if (dto.getTacos() == null) {
//            return Mono.just(tacoOrder);
//        }
//
//        return Flux.fromIterable(dto.getTacos())
//                .flatMap(tacoDtoConverter::toEntity)
//                .collectList()
//                .map(tacos -> {
//                    tacoOrder.setTacos(tacos);
//                    return tacoOrder;
//                });
//    }
//}
