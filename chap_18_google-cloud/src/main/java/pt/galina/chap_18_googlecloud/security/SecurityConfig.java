package pt.galina.chap_18_googlecloud.security;

import lombok.extern.slf4j.Slf4j;
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

import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/**", "/orders/**","/", "/login", "/register", "/static/**", "/css/**", "/images/**").permitAll()
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
                                    .then(redirectTo(webFilterExchange.getExchange(), "/design"));
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            log.error("Authentication failed", exception);
                            return redirectTo(webFilterExchange.getExchange(), "/login?error=true");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((webFilterExchange, authentication) -> {
                            log.info("User {} logged out successfully", authentication != null ? authentication.getName() : "unknown");
                            return redirectTo(webFilterExchange.getExchange(), "/");
                        })
                )
                .build();
    }

    private Mono<Void> redirectTo(ServerWebExchange exchange, String path) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(path));
        return Mono.empty();
    }
}
