package pt.galina.kafkaproducer.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.galina.kafkaproducer.dto.TacoDTO;
import pt.galina.kafkaproducer.dto.TacoOrderDTO;
import pt.galina.kafkaproducer.entity.taco.Ingredient;
import pt.galina.kafkaproducer.entity.taco.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

    private final KafkaTemplate<String, TacoOrderDTO> kafkaTemplate;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, TacoOrderDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public TacoOrderDTO convertToDto(TacoOrder order) {
        TacoOrderDTO dto = new TacoOrderDTO();
        dto.setPlacedAt(order.getPlacedAt());
        dto.setDeliveryName(order.getDeliveryName());

        List<TacoDTO> tacoDTOs = order.getTacos().stream().map(taco -> {
            TacoDTO tacoDTO = new TacoDTO();
            tacoDTO.setName(taco.getName());
            tacoDTO.setIngredients(taco.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList()));
            return tacoDTO;
        }).collect(Collectors.toList());

        dto.setTacos(tacoDTOs);

        return dto;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        TacoOrderDTO orderDTO = convertToDto(order);
        kafkaTemplate.send("tacocloud.orders.topic", orderDTO);

    }

}
