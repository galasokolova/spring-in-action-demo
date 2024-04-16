package pt.galina.rabbitmq_push_listener.message;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue myQueue() {
        return new Queue("tacocloud.order.queue", true); // true указывает на долговечность очереди
    }

    @Bean
    public Declarables topicBindings() {
        Queue myQueue = new Queue("tacocloud.order.queue", true);
        TopicExchange myExchange = new TopicExchange("tacocloud.orders");
        return new Declarables(
                myQueue,
                myExchange,
                BindingBuilder.bind(myQueue).to(myExchange).with("tacocloud.order")
        );
    }
}
