package pt.galina.chap_5_user_in_db.in_db.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                         (authz) -> authz
                                 .requestMatchers("/design", "/orders")
                                 .hasRole("USER")
                                 .requestMatchers("/", "/**").permitAll()
                 ).formLogin(form -> form
                         .loginPage("/login"))
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                 .csrf(AbstractHttpConfigurer::disable);
         return http.build();
    }
}
