package pt.galina.chap_11_reactor_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pt.galina.chap_11_reactor_demo.service.CombinationService;
import pt.galina.chap_11_reactor_demo.service.CreationService;
import pt.galina.chap_11_reactor_demo.util.Utils;

import java.util.List;

@SpringBootApplication
public class Chap11ReactorDemoApplication {
	private static final Logger log = LoggerFactory.getLogger(Chap11ReactorDemoApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Chap11ReactorDemoApplication.class, args);

		CreationService creationService = context.getBean(CreationService.class);
		CombinationService combinationService = context.getBean(CombinationService.class);

		// Creation

		creationService.createAFluxFromArray(new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"})
				.subscribe(fruit -> log.info("⏩ fromArray(Array) : {}", fruit));
		creationService.createAFluxFromIterable(List.of("Apple", "Orange", "Grape", "Banana", "Strawberry"))
				.subscribe(fruit -> log.info("⏩⏩ fromIterable(List) : {}", fruit));
		creationService.createAFluxRange(1, 5)
				.subscribe(integer -> log.info("\uD83D\uDFE1 range(1, 5) : {}", integer));

		creationService.createAFluxInterval(1L, 5)
				.subscribe(aLong -> log.info("\uD83D\uDD50 interval() prints : {}", aLong));
		Utils.sleep(6000); // 6 sec delay to wait until interval() prints 5 first elements


		// Combination

		combinationService.mergeFluxes(
				List.of("Garfield", "Kojak", "Barbossa"),
				List.of("Lasagna", "Lollipops", "Apples"),
				500L,
				250L,
				500L
		).subscribe(f -> log.info("\uD83D\uDD00 mergeWith() : {}", f));
		Utils.sleep(5000);

		combinationService.zipFluxes(
				List.of("Garfield", "Kojak", "Barbossa"),
				List.of("Lasagna", "Lollipops", "Apples")
		).subscribe(zip -> log.info("\uD83D\uDFE1 \uD83D\uDFE2 Tulip2 zipFluxes() : {}", zip));

		combinationService.zipFluxesToObject(
				List.of("Garfield", "Kojak", "Barbossa"),
				List.of("Lasagna", "Lollipops", "Apples")
		).subscribe(f -> log.info("\uD83E\uDDCD\uD83C\uDFFB\u200D♂\uFE0F + eats + \uD83C\uDF4E zip() {}", f));


	}

}
