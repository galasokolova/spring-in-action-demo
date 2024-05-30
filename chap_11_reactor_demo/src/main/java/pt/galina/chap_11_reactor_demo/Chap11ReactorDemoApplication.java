package pt.galina.chap_11_reactor_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pt.galina.chap_11_reactor_demo.service.CreationService;
import pt.galina.chap_11_reactor_demo.util.Utils;

import java.util.List;

@SpringBootApplication
public class Chap11ReactorDemoApplication {
	private static final Logger log = LoggerFactory.getLogger(Chap11ReactorDemoApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Chap11ReactorDemoApplication.class, args);

		CreationService creationService = context.getBean(CreationService.class);

		creationService.createAFluxFromArray(new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"})
				.subscribe(fruit -> log.info("⏩ fromArray(Array) : {}", fruit));
		creationService.createAFluxFromIterable(List.of("Apple", "Orange", "Grape", "Banana", "Strawberry"))
				.subscribe(fruit -> log.info("⏩⏩ fromIterable(List) : {}", fruit));
		creationService.createAFluxRange(1, 5)
				.subscribe(integer -> log.info("\uD83D\uDFE1 range(1, 5) : {}", integer));


		creationService.createAFluxInterval(1L, 5)
				.subscribe(aLong -> System.out.println("\uD83D\uDD50 interval() prints: " + aLong));
		Utils.sleep(6000); // 6 sec delay to wait until interval() prints 5 first elements


	}

}
