package pt.galina.rest_controller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeHttpRequests(
                         authz -> authz
                                 .requestMatchers("/design", "/orders", "/orders/orderList").hasRole("USER")
                                 .requestMatchers("/h2-console/**").permitAll() // Разрешение доступа к H2-консоли
                                 .anyRequest().permitAll()
                 )
                 .formLogin(form -> form.loginPage("/login"))
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                 .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                                 .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                 .csrf(csrf -> csrf
                         .ignoringRequestMatchers("/h2-console/**")); // Turning off CSRF for H2-console


         return http.build();
    }
}
