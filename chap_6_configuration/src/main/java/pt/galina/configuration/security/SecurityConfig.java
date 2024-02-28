package pt.galina.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                 .authorizeHttpRequests(             //instead of the method authorizeRequests which is deprecated (page 125 part 5.3.1)
                         authz -> authz
                                 .requestMatchers("/design", "/orders")
                                 .hasRole("USER")
                                 .requestMatchers("/", "/**").permitAll()
                 ).formLogin(form -> form
                         .loginPage("/login"))
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        // this code disables protection from csrf attacks, it's very dangerous to include it !!!
        // until it is excluded you may not leave the page /design by clicking the button "submit"
//                 .csrf(AbstractHttpConfigurer::disable)
         return http.build();
    }
}
