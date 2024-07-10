package pt.galina.chap1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap1.controller.HomeController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class Chap1ApplicationTests {

    private final MockMvc mockMvc;

    @Autowired
    Chap1ApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void shouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
    }

    @Test
    void viewShouldContainWelcomeString() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(containsString("Welcome to Taco Cloud!!!")));
    }

}
