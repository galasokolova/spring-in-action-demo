package pt.galina.chap4mongodb.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap4mongodb.data.IngredientRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WebConfig.class)
class WebConfigTest {

    private final MockMvc mockMvc;

    @MockBean
    private final IngredientRepository ingredientRepository;

    @Autowired
    WebConfigTest(MockMvc mockMvc, IngredientRepository ingredientRepository) {
        this.mockMvc = mockMvc;
        this.ingredientRepository = ingredientRepository;
    }

    @Test
    void shouldReturnHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

}