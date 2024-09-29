package pt.galina.clientreactive.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login", "/register", "/", "/static/**", "/css/**", "/images/**").permitAll()
                        .pathMatchers("/login", "/register", "/", "/static/**").permitAll()
                        .pathMatchers("/design", "/orders").hasRole("USER")
                        .pathMatchers("/applications").hasRole("ADMIN")
                        .pathMatchers("/management/**").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("User {} authenticated successfully", authentication.getName());
                            SecurityContext context = new SecurityContextImpl(authentication);
                            WebSessionServerSecurityContextRepository repository = new WebSessionServerSecurityContextRepository();
                            return repository
                                    .save(webFilterExchange.getExchange(), context)
                                    .then(Mono.fromRunnable(() -> {
                                        webFilterExchange
                                                .getExchange()
                                                .getResponse()
                                                .setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                        webFilterExchange
                                                .getExchange()
                                                .getResponse()
                                                .getHeaders()
                                                .setLocation(URI.create("/design"));
                                    }));
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            log.error("Authentication failed", exception);
                            return Mono.fromRunnable(() -> {
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .getHeaders()
                                        .setLocation(URI.create("/login?error=true"));
                            });
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("User {} logged out successfully", authentication != null ? authentication.getName() : "unknown");
                            return Mono.fromRunnable(() -> {
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .getHeaders()
                                        .setLocation(URI.create("/"));
                            });
                        })
                )
//                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
