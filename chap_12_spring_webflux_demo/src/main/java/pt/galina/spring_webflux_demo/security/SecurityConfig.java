package pt.galina.spring_webflux_demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/design", "/orders").hasRole("USER")
                        .pathMatchers("/api/**").permitAll()
                        .anyExchange().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("User {} authenticated successfully", authentication.getName());
                            SecurityContext context = new SecurityContextImpl(authentication);
                            WebSessionServerSecurityContextRepository repository = new WebSessionServerSecurityContextRepository();
                            return repository.save(webFilterExchange.getExchange(), context)
                                    .then(Mono.fromRunnable(() -> {
                                        webFilterExchange.getExchange().getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                        webFilterExchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/design"));
                                    }));
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            log.error("Authentication failed", exception);
                            return Mono.fromRunnable(() -> {
                                webFilterExchange.getExchange().getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                            });
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("User {} logged out successfully", authentication != null ? authentication.getName() : "unknown");
                            return Mono.fromRunnable(() -> {
                                webFilterExchange.getExchange().getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                webFilterExchange.getExchange().getResponse().getHeaders().setLocation(URI.create("/"));
                            });
                        })
                )
                .build();
    }
}
