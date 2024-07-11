package pt.galina.chap3jpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap3jpa.data.IngredientRepository;
import pt.galina.chap3jpa.data.OrderRepository;
import pt.galina.chap3jpa.converter.IngredientByIdConverter;
import pt.galina.chap3jpa.entity.TacoOrder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private IngredientByIdConverter ingredientByIdConverter;

    @Test
    void orderForm_ShouldReturnOrderFormView() throws Exception {
        mockMvc.perform(get("/orders/current")
                        .sessionAttr("tacoOrder", new TacoOrder()))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"));
    }

    @Test
    void processOrder_ShouldRedirectToHomePage_WhenNoErrors() throws Exception {
        mockMvc.perform(post("/orders")
                        .param("deliveryName", "John Doe")
                        .param("deliveryStreet", "1234 Main St")
                        .param("deliveryCity", "Anytown")
                        .param("deliveryState", "CA")
                        .param("deliveryZip", "12345")
                        .param("ccNumber", "4111111111111111")
                        .param("ccExpiration", "12/24")
                        .param("ccCVV", "123")
                        .sessionAttr("tacoOrder", new TacoOrder()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void processOrder_ShouldReturnOrderFormView_WhenThereAreErrors() throws Exception {
        mockMvc.perform(post("/orders")
                        .param("deliveryName", "") // Trigger validation error by setting an empty name
                        .param("deliveryStreet", "")
                        .param("deliveryCity", "")
                        .param("deliveryState", "")
                        .param("deliveryZip", "")
                        .param("ccNumber", "")
                        .param("ccExpiration", "")
                        .param("ccCVV", "")
                        .sessionAttr("tacoOrder", new TacoOrder()))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"))
                .andExpect(model().attributeHasFieldErrors("tacoOrder",
                        "deliveryName",
                        "deliveryStreet",
                        "deliveryCity",
                        "deliveryState",
                        "deliveryZip",
                        "ccNumber",
                        "ccExpiration",
                        "ccCVV"));
    }
}
