package pt.galina.chap8admin_preauthorize_demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.galina.chap8admin_preauthorize_demo.data.AdminRepository;
import pt.galina.chap8admin_preauthorize_demo.data.UserRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.admin.Admin;
import pt.galina.chap8admin_preauthorize_demo.entity.user.User;


@Configuration
@EnableMethodSecurity
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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .requestMatchers("/design", "/orders")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/", "/**").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        http.authenticationProvider(authenticationProviderUser(userRepository));
        http.authenticationProvider(authenticationProviderAdmin(adminRepository));
        return http.build();
    }
}
