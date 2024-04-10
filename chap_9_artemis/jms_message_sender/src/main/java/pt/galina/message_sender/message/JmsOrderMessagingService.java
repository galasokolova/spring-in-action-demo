package pt.galina.message_sender.message;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pt.galina.message_sender.dto.TacoDTO;
import pt.galina.message_sender.dto.TacoOrderDTO;
import pt.galina.message_sender.entity.taco.Ingredient;
import pt.galina.message_sender.entity.taco.TacoOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{
    private final JmsTemplate jms;
    private final Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
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
        jms.convertAndSend("tacocloud.order.queue", dto, JmsOrderMessagingService::addOrderSource);
    }


    private static Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
