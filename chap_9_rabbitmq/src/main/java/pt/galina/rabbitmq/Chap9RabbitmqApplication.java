package pt.galina.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.rabbitmq.config")
public class Chap9RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap9RabbitmqApplication.class, args);
    }

}

/*
http://dockerhost:15672

docker run -d --hostname dockerhost --name rabbit-name rabbitmq:3

 */