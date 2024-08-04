package pt.galina.chap8clientapp.service;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import pt.galina.chap8clientapp.entity.taco.Ingredient;

import java.util.List;
import java.util.Objects;


@Setter
@Getter
public class RestIngredientService implements IngredientService {

    private static final Logger logger = LoggerFactory.getLogger(RestIngredientService.class);

    private RestTemplate restTemplate;

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null){
            this.restTemplate
                    .getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));
        }
    }


    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        };
    }

    @Override
    public Iterable<Ingredient> findAll() {

//        Logger.getLogger(RestIngredientService.class.getName()).info("RestTemplate is set: " + restTemplate);
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "http://localhost:8080/api/ingredients",
                Ingredient.class
        )));
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(
                "http://localhost:8080/api/ingredients",
                ingredient,
                Ingredient.class
        );
    }
}
