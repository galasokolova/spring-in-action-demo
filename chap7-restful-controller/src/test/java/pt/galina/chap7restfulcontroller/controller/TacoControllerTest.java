package pt.galina.chap7restfulcontroller.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap7restfulcontroller.data.IngredientRepository;
import pt.galina.chap7restfulcontroller.data.OrderRepository;
import pt.galina.chap7restfulcontroller.data.TacoRepository;
import pt.galina.chap7restfulcontroller.data.UserRepository;
import pt.galina.chap7restfulcontroller.entity.taco.Taco;
import pt.galina.chap7restfulcontroller.entity.taco.TacoOrder;
import pt.galina.chap7restfulcontroller.entity.user.User;
import pt.galina.chap7restfulcontroller.exception.OrderNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TacoController.class)
class TacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TacoRepository tacoRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    private Taco testTaco;

    @BeforeEach
    void setup() {
        testTaco = new Taco();
        testTaco.setId(1L);
        testTaco.setName("Test Taco");



        User testUser = mock(User.class);
        when(testUser.getUsername()).thenReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(testUser);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void recentTacos_ShouldReturnRecentTacos() throws Exception {
        Pageable pageable = PageRequest.of(0, 9, Sort.by("createdAt").descending());
        given(tacoRepository.findAll(pageable)).willReturn(new PageImpl<>(Collections.singletonList(testTaco)));

        mockMvc.perform(get("/api/tacos?recent")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(testTaco.getId()))
                .andExpect(jsonPath("$[0].name").value(testTaco.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void tacoById_ShouldReturnTaco() throws Exception {
        given(tacoRepository.findById(anyLong())).willReturn(Optional.of(testTaco));

        mockMvc.perform(get("/api/tacos/1")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testTaco.getId()))
                .andExpect(jsonPath("$.name").value(testTaco.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void tacoById_ShouldReturnNotFound() throws Exception {
        given(tacoRepository.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/tacos/1")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void postTaco_ShouldReturnCreatedTaco() throws Exception {
        given(tacoRepository.save(any(Taco.class))).willReturn(testTaco);

        mockMvc.perform(post("/api/tacos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Taco\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testTaco.getId()))
                .andExpect(jsonPath("$.name").value(testTaco.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void putOrder_ShouldReturnUpdatedOrder() throws Exception {
        TacoOrder order = new TacoOrder();
        order.setId(1L);

        given(orderRepository.save(any(TacoOrder.class))).willReturn(order);

        mockMvc.perform(put("/api/tacos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"deliveryName\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void patchTacoOrder_ShouldReturnPatchedOrder() throws Exception {
        TacoOrder order = new TacoOrder();
        order.setId(1L);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(orderRepository.save(any(TacoOrder.class))).willReturn(order);

        mockMvc.perform(patch("/api/tacos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"deliveryName\":\"Patched Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void patchTacoOrder_ShouldReturnNotFound() throws Exception {
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(patch("/api/tacos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"deliveryName\":\"Patched Name\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void deleteOrder_ShouldReturnNoContent() throws Exception {
        doNothing().when(orderRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/tacos/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void deleteOrder_ShouldReturnNotFound() throws Exception {
        doThrow(new OrderNotFoundException(1L)).when(orderRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/tacos/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

}