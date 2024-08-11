package pt.galina.ordersender.security;

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
                 .authorizeHttpRequests(             //instead of the method authorizeRequests which is deprecated (page 125 part 5.3.1)
                         authz -> authz
                                 .requestMatchers("/", "/login", "/oauth2/**", "/css/**", "/images/**").permitAll()
                                 .requestMatchers("/design", "/orders", "/orders/orderList")
                                 .hasRole("USER")
                                 .anyRequest().permitAll()
                 )
                 .formLogin(
                         form -> form
                         .loginPage("/login")
                 )
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                 .headers(
                         httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                                 .frameOptions(
                                         HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                 );

         return http.build();
    }
}
