package com.exampleForVano.PP3_1_4.configs;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userService;
    private final CoderConfig coderConfig;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userService, CoderConfig coderConfig) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.coderConfig = coderConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/", "/h2-console/**").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().
                successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }



    public UserDetailsService getUserDetailsService() {
        return userService;
    }

    public CoderConfig getCoderConfig() {
        return coderConfig;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getUserDetailsService());
        authenticationProvider.setPasswordEncoder(getCoderConfig().getPasswordEncoder());
        return authenticationProvider;
    }


}