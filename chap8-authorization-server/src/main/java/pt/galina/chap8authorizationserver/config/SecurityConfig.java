package pt.galina.chap8authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.galina.chap8authorizationserver.user.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
				.authorizeHttpRequests(
						authorize -> authorize.anyRequest().authenticated()
				)
	// Form login handles the redirect to the login page from the authorization server filter chain
				.formLogin(Customizer.withDefaults());

		return http.build();
	}
	@Bean
	public UserDetailsService userDetailsService(UserRepository tacoUserRepository) {
		return tacoUserRepository::findByUsername;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

