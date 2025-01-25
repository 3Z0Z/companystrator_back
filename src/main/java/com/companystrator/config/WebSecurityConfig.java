package com.companystrator.config;

import com.companystrator.db.enums.UserRoles;
import com.companystrator.user.service.LogoutUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private LogoutUserService logoutUserService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(
					"/api/v1/user/**",
					"/api/v1/company/get-company-by-nit/**",
					"/api/v1/company/get-company-list",
					"/api/v1/product/get-product-categories",
					"/api/v1/product/get-product-by-code/**",
					"/api/v1/product/get-products-by-nit/**",
					"/swagger-ui/**",
					"/v3/api-docs/**"
				).permitAll()
				.requestMatchers(
					"/api/v1/company/create-company",
					"/api/v1/company/update-company/**",
					"/api/v1/company/delete-company/**",
					"/api/v1/product/create-product-category",
					"/api/v1/product/create-product",
					"/api/v1/product/update-product/**",
					"/api/v1/product/delete-product/**",
					"/api/v1/order/get-order-by-id/**",
					"/api/v1/order/get-orders-by-nit/**"
				).hasAuthority(UserRoles.ADMIN.toString())
				.requestMatchers(
					"/api/v1/order/place-order",
					"/api/v1/order/get-order-by-id/**"
				).hasAuthority(UserRoles.CLIENT.toString())
				.anyRequest().authenticated()
			)
			.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
				.logoutUrl("/api/v1/user/logout")
				.addLogoutHandler(logoutUserService)
				.logoutSuccessHandler(
					((request, response, authentication) -> SecurityContextHolder.clearContext())
				)
			);
		return http.build();
	}

}
