//package com.training.product_service.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests((authorizeRequests) -> 
//				authorizeRequests.requestMatchers("/products").hasRole("ADMIN")
//				.requestMatchers("/products/**").hasRole("ADMIN")
//				.requestMatchers("/products/{id}").permitAll()
//				.anyRequest().authenticated()
//		)
//		 .formLogin(form -> form
//             .permitAll()    
//		);
//		return http.build();
//		
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("admin")
//            .password(passwordEncoder().encode("adminpassword"))
//            .roles("ADMIN")
//            .build());
//        manager.createUser(User.withUsername("user")
//            .password(passwordEncoder().encode("adminpassword"))
//            .roles("USER")
//            .build());
//        return manager;
//    }
//}
