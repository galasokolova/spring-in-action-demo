package pt.galina.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.jms.config")

public class Chap9JmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap9JmsApplication.class, args);
    }

}
