package pt.galina.chap6loggingconfig.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap6loggingconfig.data.IngredientRepository;
import pt.galina.chap6loggingconfig.entity.taco.Ingredient;
import pt.galina.chap6loggingconfig.entity.user.User;
import pt.galina.chap6loggingconfig.data.UserRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepo;

    @MockBean
    private UserRepository userRepo;

    private List<Ingredient> ingredients;

    @BeforeEach
    void setup() {
        ingredients = List.of(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        Mockito.when(ingredientRepo.findAll()).thenReturn(ingredients);
        Mockito.when(userRepo.findByUsername(anyString())).thenReturn(new User());
    }

    @Test
    @WithMockUser(username="user")
    void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeExists("wrap", "protein", "veggies", "cheese", "sauce", "tacoOrder", "taco", "user"));
    }

    @Test
    @WithMockUser(username="user")
    void testProcessTacoWithErrors() throws Exception {
        mockMvc.perform(post("/design")
                        .param("name", "") // Invalid Taco name
                        .param("ingredients", "FLTO,GRBF")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeHasFieldErrors("taco", "name"));
    }

    @Test
    @WithMockUser(username="user")
    void testProcessTacoWithoutErrors() throws Exception {
        mockMvc.perform(post("/design")
                        .param("name", "Test Taco")
                        .param("ingredients", "FLTO,GRBF")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));
    }
}
