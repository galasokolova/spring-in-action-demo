package pt.galina.chap3jpa.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap3jpa.data.IngredientRepository;
import pt.galina.chap3jpa.entity.Ingredient;
import pt.galina.chap3jpa.entity.Taco;
import pt.galina.chap3jpa.entity.TacoOrder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN)
        );
        given(ingredientRepository.findAll()).willReturn(ingredients);
    }

    @Test
    void showDesignForm_ShouldReturnDesignView() throws Exception {
        mockMvc.perform(get("/design")
                        .sessionAttr("tacoOrder", new TacoOrder())
                        .flashAttr("taco", new Taco()))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    void processTaco_ShouldRedirectToCurrentOrder() throws Exception {
        mockMvc.perform(post("/design")
                        .param("name", "TestTaco")
                        .param("ingredients", "FLTO", "GRBF")
                        .sessionAttr("tacoOrder", new TacoOrder()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));
    }

    @Test
    void processTaco_WithErrors_ShouldReturnToDesignView() throws Exception {
        mockMvc.perform(post("/design")
                        .param("name", "Test Taco")
                        .sessionAttr("tacoOrder", new TacoOrder()))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeHasFieldErrors("taco", "ingredients"));
    }
}
