package pt.galina.jms_receiver.message;

import org.springframework.core.convert.converter.Converter;
import pt.galina.jms_receiver.dto.TacoDTO;
import pt.galina.jms_receiver.dto.TacoOrderDTO;
import pt.galina.jms_receiver.entity.taco.Ingredient;
import pt.galina.jms_receiver.entity.taco.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderToDtoConverter implements Converter<TacoOrder, TacoOrderDTO> {
    @Override
    public TacoOrderDTO convert(TacoOrder order) {
        TacoOrderDTO dto = new TacoOrderDTO();
        dto.setPlacedAt(order.getPlacedAt());
        dto.setDeliveryName(order.getDeliveryName());

        List<TacoDTO> tacos = order.getTacos().stream().map(taco -> {
            TacoDTO tacoDTO = new TacoDTO();
            tacoDTO.setName(taco.getName());
            tacoDTO.setIngredients(taco.getIngredients().stream()
                    .map(Ingredient::getName)
                    .collect(Collectors.toList()));
            return tacoDTO;
        }).collect(Collectors.toList());

        dto.setTacos(tacos);
        return dto;
    }
}
