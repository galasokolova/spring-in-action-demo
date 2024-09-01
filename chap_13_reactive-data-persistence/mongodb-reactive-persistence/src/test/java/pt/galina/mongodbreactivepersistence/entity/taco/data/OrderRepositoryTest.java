package pt.galina.mongodbreactivepersistence.entity.taco.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.galina.mongodbreactivepersistence.config.TestConfig;
import pt.galina.mongodbreactivepersistence.entity.taco.TacoOrder;
import pt.galina.mongodbreactivepersistence.entity.user.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;

@DataMongoTest
@Import(TestConfig.class)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        // Создаем тестового пользователя
        testUser = new User();
        testUser.setId("user1");
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setFullname("Test User");
        testUser.setStreet("123 Street");
        testUser.setCity("City");
        testUser.setState("State");
        testUser.setZip("12345");
        testUser.setPhone("123-456-7890");

        // Очищаем репозиторий перед каждым тестом
        orderRepository.deleteAll().block();

        // Создаем несколько заказов для пользователя
        TacoOrder order1 = new TacoOrder();
        order1.setId("order1");
        order1.setUser(testUser);
        order1.setPlacedAt(LocalDateTime.now().minusDays(1)); // Заказ создан вчера

        TacoOrder order2 = new TacoOrder();
        order2.setId("order2");
        order2.setUser(testUser);
        order2.setPlacedAt(LocalDateTime.now().minusDays(2)); // Заказ создан позавчера

        TacoOrder order3 = new TacoOrder();
        order3.setId("order3");
        order3.setUser(testUser);
        order3.setPlacedAt(LocalDateTime.now()); // Заказ создан сегодня

        // Сохраняем заказы в репозиторий
        orderRepository.saveAll(Arrays.asList(order1, order2, order3)).blockLast();
    }

    @Test
    public void testFindByUserOrderByPlacedAtDesc() {
        Pageable pageable = PageRequest.of(0, 2); // Страница 0, размер страницы 2

        Flux<TacoOrder> orders = orderRepository.findByUserOrderByPlacedAtDesc(testUser, pageable);

        StepVerifier.create(orders)
                .expectNextMatches(order -> order.getId().equals("order3")) // Заказ, созданный сегодня
                .expectNextMatches(order -> order.getId().equals("order1")) // Заказ, созданный вчера
                .verifyComplete();
    }
}
