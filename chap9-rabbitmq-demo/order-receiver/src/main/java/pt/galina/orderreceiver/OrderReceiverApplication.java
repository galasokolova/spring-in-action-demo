package pt.galina.orderreceiver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pt.galina.orderreceiver.data.IngredientRepository;
import pt.galina.orderreceiver.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class OrderReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderReceiverApplication.class, args);
    }

}
