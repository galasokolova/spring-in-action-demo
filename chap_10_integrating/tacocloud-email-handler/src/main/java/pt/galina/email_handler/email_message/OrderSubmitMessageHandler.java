package pt.galina.email_handler.email_message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pt.galina.email_handler.taco.EmailOrder;

@Component
public class OrderSubmitMessageHandler  implements GenericHandler<EmailOrder> {
    private final Logger log = LoggerFactory.getLogger(OrderSubmitMessageHandler.class);


    private final RestTemplate rest;
    private final ApiProperties apiProps;

    public OrderSubmitMessageHandler(ApiProperties apiProps, RestTemplate rest) {
        this.apiProps = apiProps;
        this.rest = rest;
    }

    @Override
    public Object handle(EmailOrder  order, MessageHeaders headers) {
        log.info("\uD83D\uDE9B \uD83D\uDE9B \uD83D\uDE9B Sending order to API: {}", order);
        rest.postForObject(apiProps.getUrl(), order,  String.class);
        return null;
    }
}
