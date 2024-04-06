package pt.galina.taco_kitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/*
http://localhost:8081/receivedOrder
 */
@SpringBootApplication
public class Chap9TacoKitchenApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap9TacoKitchenApplication.class, args);
	}

}
