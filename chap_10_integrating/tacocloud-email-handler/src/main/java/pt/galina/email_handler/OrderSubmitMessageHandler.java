package pt.galina.email_handler;

import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pt.galina.email_handler.taco.EmailOrder;

@Component
public class OrderSubmitMessageHandler  implements GenericHandler<EmailOrder> {
    private final RestTemplate rest;
    private final ApiProperties apiProps;

    public OrderSubmitMessageHandler(ApiProperties apiProps, RestTemplate rest) {
        this.apiProps = apiProps;
        this.rest = rest;
    }

    @Override
    public Object handle(EmailOrder  order, MessageHeaders headers) {
        rest.postForObject(apiProps.getUrl(), order,  String.class);
        return null;
    }
}
