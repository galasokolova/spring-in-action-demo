package pt.galina.chap5methodlevelsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.galina.chap5methodlevelsecurity.security.error.Custom403AccessDeniedHandler;
import pt.galina.chap5methodlevelsecurity.entity.admin.Admin;
import pt.galina.chap5methodlevelsecurity.entity.admin.AdminRepository;
import pt.galina.chap5methodlevelsecurity.entity.user.User;
import pt.galina.chap5methodlevelsecurity.entity.user.UserRepository;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    Custom403AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(Custom403AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

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
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasAnyRole( "USER", "ADMIN")
                        .requestMatchers("/design", "/orders").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/", "/**").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)   // Custom 403 handler
                );

        http.authenticationProvider(authenticationProviderUser(userRepository));
        http.authenticationProvider(authenticationProviderAdmin(adminRepository));

        return http.build();
    }

}

