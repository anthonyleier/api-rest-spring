package br.com.anthonycruz.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.anthonycruz.security.jwt.JwtTokenFilter;
import br.com.anthonycruz.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	PasswordEncoder passwordEncoder() {
		String algorithmType = "pbkdf2";
		Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put(algorithmType, encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(algorithmType, encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);

		return passwordEncoder;
	}

	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
        return http
        		.httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
            		session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                    authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
							"/auth/signin",
							"/auth/refresh/**",
                    		"/swagger-ui/**",
                    		"/v3/api-docs/**"
                		).permitAll()
                        .requestMatchers("/persons/**").authenticated()
                        .requestMatchers("/books/**").authenticated()
                        .requestMatchers("/files/**").authenticated()
                        .requestMatchers("/users").denyAll()
                )
                .cors(cors -> {})
                .build();
    }
}
