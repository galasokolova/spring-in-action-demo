package pt.galina.tacocloudapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;

/*
This class is for sending orders to main app after handling them in tacocloud-api, f.ex. to kitchen-service
 */

@Configuration
public class KitchenIntegrationConfig {

    @Bean
    public IntegrationFlow sendOrderToKitchenFlow() {
        return IntegrationFlow.from("orderChannel")
                .handle(Http.outboundGateway("${tacocloud.api.url}/processOrder")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(String.class))
                .get();
    }
}

