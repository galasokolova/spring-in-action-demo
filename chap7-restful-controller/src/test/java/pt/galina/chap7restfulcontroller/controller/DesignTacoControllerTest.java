package pt.galina.chap7restfulcontroller.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap7restfulcontroller.data.IngredientRepository;
import pt.galina.chap7restfulcontroller.data.UserRepository;
import pt.galina.chap7restfulcontroller.entity.taco.Ingredient;
import pt.galina.chap7restfulcontroller.entity.taco.Taco;
import pt.galina.chap7restfulcontroller.entity.taco.TacoOrder;
import pt.galina.chap7restfulcontroller.entity.user.User;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = { DesignTacoController.class })
class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup(){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN)
        );
        given(ingredientRepository.findAll()).willReturn(ingredients);

        user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(user);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void showDesignForm_ShouldReturnDesignView() throws Exception {
        mockMvc.perform(get("/design")
                        .with(csrf())
                        .sessionAttr("tacoOrder", new TacoOrder())
                        .sessionAttr("user", user)
                        .flashAttr("taco", new Taco()))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void processTaco_ShouldRedirectToCurrentOrder() throws Exception {
        mockMvc.perform(post("/design")
                        .with(csrf())
                        .param("name", "TestTaco")
                        .param("ingredients", "FLTO", "GRBF")
                        .sessionAttr("tacoOrder", new TacoOrder())
                        .sessionAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void processTaco_WithErrors_ShouldReturnToDesignView() throws Exception {
        mockMvc.perform(post("/design")
                .param("name", "TestTaco")
                .sessionAttr("tacoOrder", new TacoOrder())
                .with(csrf()))

                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeHasFieldErrors("taco", "ingredients"));
    }

}
