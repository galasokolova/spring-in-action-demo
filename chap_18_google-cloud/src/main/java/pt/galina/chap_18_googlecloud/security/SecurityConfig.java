package pt.galina.chap_18_googlecloud.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import pt.galina.chap_18_googlecloud.security.csrf.CustomServerCsrfTokenRepository;
import pt.galina.chap_18_googlecloud.security.csrf.MongoCsrfTokenRepository;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final MongoCsrfTokenRepository csrfTokenRepository;

    public SecurityConfig(MongoCsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                //Persisting csrf token in MongoDB database
                .csrf(csrfSpec -> csrfSpec.csrfTokenRepository(new CustomServerCsrfTokenRepository(csrfTokenRepository))
                )
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/", "/login", "/register", "/static/**", "/css/**", "/images/**").permitAll()
                        .pathMatchers("/design", "/orders").hasRole("USER")
                        .anyExchange().permitAll()
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
                .build();
    }
}
