package pt.galina.method_level_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.galina.method_level_security.entity.admin.AdminRepositoryDetailsService;
import pt.galina.method_level_security.entity.user.UserRepositoryDetailsService;


@Configuration
public class SecurityConfig {

    private final UserRepositoryDetailsService userDetailsService;
    private final AdminRepositoryDetailsService adminRepositoryDetailsService;

    public SecurityConfig(UserRepositoryDetailsService userDetailsService, AdminRepositoryDetailsService adminRepositoryDetailsService) {
        this.userDetailsService = userDetailsService;
        this.adminRepositoryDetailsService = adminRepositoryDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(adminRepositoryDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeHttpRequests(
                         authz -> authz
                                 .requestMatchers("/admin/**")
                                 .hasRole("ADMIN")
                                 .requestMatchers("/design", "/orders")
                                 .hasRole("USER")
                                 .requestMatchers("/", "/**").permitAll()
                 ).formLogin(form -> form
                         .loginPage("/login"))
                 .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
         http.authenticationProvider(authenticationProvider());
         http.authenticationProvider(authenticationProvider1());
         return http.build();
    }
}

