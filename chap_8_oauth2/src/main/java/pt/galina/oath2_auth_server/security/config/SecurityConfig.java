package pt.galina.oath2_auth_server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.galina.oath2_auth_server.data.UserRepository;
import pt.galina.oath2_auth_server.entity.admin.Admin;
import pt.galina.oath2_auth_server.data.AdminRepository;
import pt.galina.oath2_auth_server.entity.user.User;


@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProviderUser(UserRepository userRepo) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(
                username -> {
                    User user = userRepo.findByUsername(username);
                    if (user != null) {
                        return user;
                    }
                    throw new UsernameNotFoundException(
                            "User '" + username + "' not found");
                }
        );
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProviderAdmin(AdminRepository adminRepo) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(
                username -> {
                    Admin admin = adminRepo.findByUsername(username);
                    if (admin != null) {
                        return admin;
                    }
                    throw new UsernameNotFoundException(
                            "Admin '" + username + "' not found");
                }
        );
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserRepository userRepository,
                                           AdminRepository adminRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/design", "/orders")
                                .hasRole("USER")
                                .requestMatchers("/", "/**").permitAll()
                ).formLogin(form -> form
                        .loginPage("/login"))
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        http.authenticationProvider(authenticationProviderUser(userRepository));
        http.authenticationProvider(authenticationProviderAdmin(adminRepository));
        return http.build();
    }
}
/*
http
    .authorizeRequests()
        .antMatchers("/h2-console/**").permitAll()
        .and()
    .csrf()
        .ignoringAntMatchers("/h2-console/**")
        .and()
    .headers()
        .frameOptions()
        .sameOrigin();
 */