package pt.galina.rabbitmq_order_sender.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.galina.rabbitmq_order_sender.dto.TacoDTO;
import pt.galina.rabbitmq_order_sender.dto.TacoOrderDTO;
import pt.galina.rabbitmq_order_sender.entity.taco.Ingredient;
import pt.galina.rabbitmq_order_sender.entity.taco.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
    public void convertAndSendOrder(TacoOrder order) {
        TacoOrderDTO dto = convertToDto(order);
        rabbitTemplate.convertAndSend("tacocloud.orders", "tacocloud.order",dto, RabbitOrderMessagingService::addOrderSource);
    }

    private static Message addOrderSource(Message message) {
        MessageProperties props = message.getMessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        props.setHeader("__TypeId__", TacoOrderDTO.class.getName());
        return message;
    }
}
