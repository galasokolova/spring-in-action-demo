package pt.galina.adminserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/management/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
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
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
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
                            return Mono.fromRunnable(() -> {
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .setStatusCode(org.springframework.http.HttpStatus.FOUND);
                                webFilterExchange
                                        .getExchange()
                                        .getResponse()
                                        .getHeaders()
                                        .setLocation(URI.create("/login?logout=true"));
                            });
                        })
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}


