package pt.galina.client_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.client_app.config")
public class Chap8ClientAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap8ClientAppApplication.class, args);
    }

}
