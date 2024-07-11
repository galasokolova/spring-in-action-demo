package pt.galina.chap2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap2.entity.Taco;
import pt.galina.chap2.entity.TacoOrder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    DesignTacoControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
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
