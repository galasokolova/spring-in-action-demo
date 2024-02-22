package pt.galina.csrf.security;

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
                         authz -> authz
                                 .requestMatchers("/design", "/orders")
                                 .hasRole("USER")
                                 .requestMatchers("/", "/**").permitAll()
                 ).formLogin(form -> form
                         .loginPage("/login"))
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

// "If you’re using Spring MVC’s JSP tag library or Thymeleaf with the Spring Security dialect,
// you needn’t even bother explicitly including a hidden field. The hidden field will
//be rendered automatically for you." -  (page 134 part 5.3.4 "Spring in Action")

        //the following code disables protection from csrf attacks, and it's very dangerous to include it !!!
//                 .csrf(AbstractHttpConfigurer::disable)
         return http.build();
    }
}
