package com.example.classicmodelsweb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
	@Bean
	public UserDetailsManager detailsManager(final DataSource dataSource) {
		JdbcUserDetailsManager manager=new JdbcUserDetailsManager(dataSource);
		manager.setUsersByUsernameQuery("select username,password,active from sec_login where username=?");
		manager.setAuthoritiesByUsernameQuery("select username,role from sec_role where username=?");
		return manager;
	}
	
	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		.authorizeHttpRequests()
		.antMatchers("/","/Product/view/**","/ProductLine/view/**","/css/**").permitAll()
		.antMatchers("/Customer/updateProfile/**","/Customer/viewProfile/**","/Customer/updatePassword/**").hasRole("CUSTOMER")
		.antMatchers("/Order/**","/OderDetail/**","/Payment/**").hasAnyRole("CUSTOMER","EMPLOYEE")
		.antMatchers("/Customer/**","/Employee/**","/Office/**","/Product/**").hasRole("EMPLOYEE")
		.anyRequest().authenticated()
		.and()
		
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/verifyLogin")
		.permitAll()
		.and()
		
		.logout()
		.logoutUrl("/logout")
		.permitAll();
		
		return httpSecurity.build();
	}
}