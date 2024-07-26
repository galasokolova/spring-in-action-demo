package pt.galina.chap7restfulcontroller.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap7restfulcontroller.data.IngredientRepository;
import pt.galina.chap7restfulcontroller.data.UserRepository;
import pt.galina.chap7restfulcontroller.entity.taco.Ingredient;
import pt.galina.chap7restfulcontroller.entity.user.User;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    private Ingredient testIngredient;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        testIngredient = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);

        user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(user);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void getIngredientById_ShouldReturnIngredient() throws Exception {
        given(ingredientRepository.findById(anyString())).willReturn(Optional.of(testIngredient));

        mockMvc.perform(get("/api/ingredients/FLTO")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testIngredient.getId()))
                .andExpect(jsonPath("$.name").value(testIngredient.getName()))
                .andExpect(jsonPath("$.type").value(testIngredient.getType().toString()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void getIngredientById_ShouldReturnNotFound() throws Exception {
        given(ingredientRepository.findById(anyString())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/ingredients/FLTO")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void addIngredient_ShouldReturnCreatedIngredient() throws Exception {
        given(ingredientRepository.save(any(Ingredient.class))).willReturn(testIngredient);

        mockMvc.perform(post("/api/ingredients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"FLTO\", \"name\":\"Flour Tortilla\", \"type\":\"WRAP\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testIngredient.getId()))
                .andExpect(jsonPath("$.name").value(testIngredient.getName()))
                .andExpect(jsonPath("$.type").value(testIngredient.getType().toString()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void deleteIngredient_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/ingredients/FLTO")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
