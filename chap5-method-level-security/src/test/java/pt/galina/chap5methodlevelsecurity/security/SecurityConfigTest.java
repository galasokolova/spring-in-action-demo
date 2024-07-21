package pt.galina.chap5methodlevelsecurity.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pt.galina.chap5methodlevelsecurity.entity.admin.AdminRepository;
import pt.galina.chap5methodlevelsecurity.entity.user.UserRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testPublicEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())  // Ожидаем статус 200 (OK) для публичного URL
                .andExpect(view().name("home"));  // Ожидаем, что будет возвращено представление "home"
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())  // Ожидаем статус 200 (OK)
                .andExpect(view().name("login"));  // Ожидаем, что будет возвращено представление "login"
    }

    @Test
    void testLoginWithInvalidUser() throws Exception {
        Mockito.when(userRepository.findByUsername("invalid")).thenReturn(null);

        mockMvc.perform(post("/login").with(csrf())
                        .param("username", "invalid")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())  // Ожидаем редирект (3xx)
                .andExpect(redirectedUrl("/login?error"));  // Ожидаем редирект на URL "/login?error"
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testAccessUserEndpointWithUserRole() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk());  // Ожидаем статус 200 (OK) для авторизованного пользователя
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAccessAdminEndpointWithAdminRole() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());  // Ожидаем статус 200 (OK) для авторизованного администратора
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testAccessAdminEndpointWithUserRole() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());  // Ожидаем статус 403 (Forbidden) для пользователя с ролью USER
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testLogout() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())  // Ожидаем редирект (3xx)
                .andExpect(redirectedUrl("/"));  // Ожидаем редирект на URL "/login?logout"
    }

}
