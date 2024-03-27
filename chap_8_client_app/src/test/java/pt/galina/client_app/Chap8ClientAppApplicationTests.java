package pt.galina.client_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.galina.client_app.entity.taco.Ingredient;
import pt.galina.client_app.entity.taco.Ingredient.Type;
import pt.galina.client_app.service.RestIngredientService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Chap8ClientAppApplicationTests.class)
class Chap8ClientAppApplicationTests {
//    private RestIngredientService ingredientService;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ingredientService = new RestIngredientService("dummy_access_token");
//        ingredientService.setRestTemplate(restTemplate);
//        System.out.println("RestTemplate is set: " + ingredientService.getRestTemplate()); // Debug output
//    }
//
//    @Test
//    void testFindAll() {
//        // Ожидаемые данные
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        Ingredient[] expectedIngredientsArray = {
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        };
//        ResponseEntity<Ingredient[]> responseEntity = new ResponseEntity<>(expectedIngredientsArray, headers, HttpStatus.OK);
//
//        when(restTemplate.getForEntity("http://localhost:8080/api/ingredients", Ingredient[].class))
//                .thenReturn(responseEntity);
//
//        // Выполнение теста
//        Iterable<Ingredient> ingredients = ingredientService.findAll();
//
//        // Проверка результатов
//        assertNotNull(ingredients); // Проверка, что список не пустой
//        List<Ingredient> expectedIngredientList = Arrays.asList(expectedIngredientsArray);
//        assertEquals(expectedIngredientList, ingredients); // Проверка, что полученный список совпадает с ожидаемым списком
//        verify(restTemplate, times(1)).getForEntity("http://localhost:8080/api/ingredients", Ingredient[].class);
//    }


    @Test
    void contextLoads() {
    }

}
