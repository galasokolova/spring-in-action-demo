package pt.galina.chap_11_reactor_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pt.galina.chap_11_reactor_demo.service.CombinationService;
import pt.galina.chap_11_reactor_demo.service.CreationService;
import pt.galina.chap_11_reactor_demo.service.LogicService;
import pt.galina.chap_11_reactor_demo.service.TransformationService;
import pt.galina.chap_11_reactor_demo.util.Utils;

import java.util.List;

@SpringBootApplication
public class Chap11ReactorDemoApplication {
	private static final Logger log = LoggerFactory.getLogger(Chap11ReactorDemoApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Chap11ReactorDemoApplication.class, args);

		CreationService creationService = context.getBean(CreationService.class);
		CombinationService combinationService = context.getBean(CombinationService.class);
		TransformationService transformationService = context.getBean(TransformationService.class);
		LogicService logicService = context.getBean(LogicService.class);


		log.info("Creation methods demo ---------------------------------------------------------------------------------------------");
		creationService.createAFluxFromArray(new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"})
				.subscribe(fruit -> log.info("⏩ fromArray(Array) : {}", fruit));
		creationService.createAFluxFromIterable(List.of("Apple", "Orange", "Grape", "Banana", "Strawberry"))
				.subscribe(fruit -> log.info("⏩⏩ fromIterable(List) : {}", fruit));
		creationService.createAFluxRange(1, 5)
				.subscribe(integer -> log.info("\uD83D\uDFE1 range(1, 5) : {}", integer));

		creationService.createAFluxInterval(1L, 5)
				.subscribe(aLong -> log.info("\uD83D\uDD50 interval() prints : {}", aLong));
		Utils.sleep(6000); // 6 sec delay to wait until interval() prints 5 first elements


		log.info("Combination methods demo ---------------------------------------------------------------------------------------------");
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

		combinationService.firstWithSignalFlux(
				List.of("tortoise", "snail", "sloth"),
				List.of("hare", "cheetah", "squirrel")
		).subscribe(f -> log.info("firstWithSignal() ->>> \uD83D\uDC06: {} ",  f));


		log.info("Transformation methods demo----------------------------------------------------------------------------------------");

		transformationService.skipAFew(
				List.of("one", "two", "skip a few", "ninety nine", "one hundred"),
				3
		).subscribe(f -> log.info("\uD83C\uDFA0 We did not skip : {}", f));

		transformationService.skipAFewSeconds(
				List.of("one", "two", "skip a few", "ninety nine", "one hundred"),
				1,
				4
		).subscribe(f -> log.info("✈️ skip 4 seconds: {}", f));

		Utils.sleep(7000);

		transformationService.take(
				List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"),
				3
		).subscribe(f -> log.info("\uD83D\uDE8E take() : {}", f));

		transformationService.takeForAwhile(
				List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"),
				1,
				3500
		).subscribe(f -> log.info("⌛️ takeForAwhile() : {}", f));

		Utils.sleep(5000);

		transformationService.filter(List.of("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"))
				.subscribe(f -> log.info("\uD83D\uDFEA  filter() : {}", f));

		transformationService.distinct(List.of("dog", "cat", "bird", "dog", "bird", "anteater"))
				.subscribe(f -> log.info("\uD83D\uDFE9 distinct() : {}", f));

		transformationService.map(List.of("Michael Jordan", "Scottie Pippen", "Steve Kerr"))
				.subscribe(f -> log.info("\uD83C\uDFC0 map() : {}", f));

		transformationService.flatMap(List.of("Michael Jordan", "Scottie Pippen", "Steve Kerr"))
				.subscribe(f -> log.info("\uD83C\uDFC0 \uD83C\uDFC0 flatMap() : {}", f));
		Utils.sleep(5000);

		transformationService.buffer(
				List.of("apple", "orange", "banana", "kiwi", "strawberry"),
						3)
				.subscribe(f -> log.info("\uD83C\uDF53 buffer(3) : {}", f));

		transformationService.bufferAndFlatMap(
				3,
				"apple", "orange", "banana", "kiwi", "strawberry"
		).subscribe(f -> log.info("\uD83C\uDF53 \uD83C\uDF53 bufferAndFlatMap(3) : {}", f));

		transformationService.collectList("apple", "orange", "banana", "kiwi", "strawberry")
				.subscribe(f -> log.info("\uD83C\uDF53 \uD83C\uDF4F \uD83C\uDF52 collectList(3) : {}", f));

		transformationService.collectMap("aardvark", "elephant", "koala", "eagle", "kangaroo")
				.subscribe(f -> log.info("\uD83D\uDDDD \uD83D\uDD12 collectMap() : {}", f));

		log.info("Logic methods demo -------------------------------------------------------------------------------------------");

		logicService.all("a", "aardvark", "elephant", "koala", "eagle", "kangaroo")
				.subscribe(f -> log.info("☑️ all() contain A : {}", f));

		logicService.any("w", "aardvark", "elephant", "koala", "eagle", "kangaroo")
				.subscribe(f -> log.info("☑️ any() contain W : {}", f));

	}

}


