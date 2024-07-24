package pt.galina.chap6loggingconfig.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap6loggingconfig.config.OrderProps;
import pt.galina.chap6loggingconfig.data.IngredientRepository;
import pt.galina.chap6loggingconfig.data.OrderRepository;
import pt.galina.chap6loggingconfig.data.UserRepository;
import pt.galina.chap6loggingconfig.entity.taco.TacoOrder;
import pt.galina.chap6loggingconfig.entity.user.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepo;

    @MockBean
    private IngredientRepository ingredientRepo;

    @MockBean
    private OrderProps orderProps;

    @MockBean
    private UserRepository userRepo;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getFullname()).thenReturn("Test User");
        Mockito.when(user.getStreet()).thenReturn("123 Test Street");
        Mockito.when(user.getCity()).thenReturn("Test City");
        Mockito.when(user.getState()).thenReturn("TS");
        Mockito.when(user.getZip()).thenReturn("12345");

        Mockito.when(orderProps.getPageSize()).thenReturn(10);
        Mockito.when(orderRepo.findByUserOrderByPlacedAtDesc(eq(user), any()))
                .thenReturn(List.of(new TacoOrder()));

        Mockito.when(userRepo.findByUsername(anyString())).thenReturn(user);

        session = new MockHttpSession();
        session.setAttribute("tacoOrder", new TacoOrder());
    }


    @Test
    @WithMockUser(username="user")
    void testProcessOrderWithErrors() throws Exception {
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .principal(() -> "user")
                        .session(session)
                        .param("deliveryName", "")
                        .param("deliveryStreet", "")
                        .param("deliveryCity", "")
                        .param("deliveryState", "")
                        .param("deliveryZip", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"))
                .andExpect(model().attributeHasFieldErrors("tacoOrder", "deliveryName", "deliveryStreet", "deliveryCity", "deliveryState", "deliveryZip"));
    }

    @Test
    @WithMockUser(username="user")
    void testProcessOrderWithoutErrors() throws Exception {
        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .principal(() -> "user")
                        .session(session)
                        .param("deliveryName", "Test User")
                        .param("deliveryStreet", "123 Test Street")
                        .param("deliveryCity", "Test City")
                        .param("deliveryState", "TS")
                        .param("deliveryZip", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/orderList"));
    }

    @Test
    @WithMockUser(username="user")
    void testOrdersForUser() throws Exception {
        mockMvc.perform(get("/orders/orderList")
                        .principal(() -> "user"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderList"))
                .andExpect(model().attributeExists("orders"));
    }
}
