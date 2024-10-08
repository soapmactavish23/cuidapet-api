package com.hkprogrammer.api.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.hkprogrammer.api.core.security.JWTConverter;

@Configuration
public class SecurityConfig {

	@Bean
	@SuppressWarnings({ "deprecation", "removal" })
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter())));

		http.authorizeRequests()
			.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
			.requestMatchers(HttpMethod.POST, "/auth/login/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()
			.requestMatchers(HttpMethod.PATCH, "/auth/confirm").hasRole("USER")
			.requestMatchers(HttpMethod.GET, "/agendamentos").hasRole("USER").anyRequest()
			.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

}
