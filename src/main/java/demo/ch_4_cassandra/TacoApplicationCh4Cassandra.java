//package demo.ch_4_cassandra;
//
//import demo.ch_4_cassandra.data.IngredientRepository;
//import demo.ch_4_cassandra.entity.Ingredient;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
///**
// 1. Start Cassandra:
// docker run --rm -d --name cassandra --hostname cassandra -p 9042:9042 cassandra
//
// 2.Open cqlsh:
// docker exec -it cassandra cqlsh
//
// 3. Create KEYSPACE "taco_cloud":
// CREATE KEYSPACE IF NOT EXISTS taco_cloud WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
//
// 4. Open app on localhost:8080/design
//
// 5. Stop Cassandra:
// docker stop cassandra
// */
//
//@SpringBootApplication
//public class TacoApplicationCh4Cassandra {
//
//    public static void main(String[] args) {
//        SpringApplication.run(TacoApplicationCh4Cassandra.class, args);
//    }
//
//    @Bean
//    public CommandLineRunner dataLoader(IngredientRepository repo) {
//        return args -> {
//            repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
//            repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
//            repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//            repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
//            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
//            repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
//            repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
//            repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
//            repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
//            repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
//        };
//    }
//
//}