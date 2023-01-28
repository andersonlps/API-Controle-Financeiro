package com.api.meusgastos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

    @Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationConfiguration authConfig;

	@Autowired
	private UserDetailsSecurityServer userDetailsSecurityServer;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.headers().frameOptions().disable().and()
				.cors().and()
				.csrf().disable()
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/api/usuarios/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/api/dashboard/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilter(new JwtAuthenticationFilter(authenticationManager(authConfig), jwtUtil));
		http.addFilter(new JwtAuthorizationFilter(authenticationManager(authConfig), jwtUtil, userDetailsSecurityServer));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//configuracao do CORS
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/", "http://127.0.0.1:5173/", "http://localhost:5174/", "exp://192.168.0.168:19001", "http://localhost:19006", "exp://192.168.0.168:19006"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		return source;
		
	}

}
