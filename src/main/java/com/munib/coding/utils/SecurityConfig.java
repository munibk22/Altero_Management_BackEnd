package com.munib.coding.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.munib.coding.jwt.JwtRequestFilter;
import com.munib.coding.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity //Enable security config 9J-b6OlPy24
public class SecurityConfig {
	@Autowired
	private UserDetailsServiceImpl myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
//	  @Bean
//	    public UserDetailsService userDetailsService() {
//	        // Create an in-memory user for demonstration purposes
//	        UserDetails user = User.builder()
//	                .username("user")
//	                .password("$2a$12$my8Hqs9b7GLHJRCm3v8Oa.vK1ec5L6YGxYMcLnod6Q7E.fnFSnKji")
//	                .roles("USER")
//	                .build();
//	        UserDetails adminUser = User.builder()
//	        		.username("sa")
//	        		.password("$2a$12$p/iuLPmRxFbF1ribWl4BpufkfQIHjg24/ATz7zz7qiTinCa7J7O8u")
//	        		.roles("ADMIN","USER")
//	        		.build();
//
//	        // Return an in-memory user details manager with the created user
//	        return new InMemoryUserDetailsManager(user, adminUser);
//	    }
	  
	  
	@Bean
	public UserDetailsService userDetailsService() {
		return myUserDetailsService;
	}
	 @Bean
	 public AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		 provider.setUserDetailsService(myUserDetailsService);
		 provider.setPasswordEncoder(passwordEncoder());
		 
		 return provider;
	 }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }        
    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> {
                authz.requestMatchers("/api/authenticate",
                		"/user/register", "/api/public/**","/login").permitAll();
                authz.requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated(); })
                        .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.oauth2Login()
//		.defaultSuccessUrl("/loginSuccess")
//		.failureUrl("/loginFailure");
    	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    	
    	return http.build();
    }



	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**");
	}
	
	
	 
}
