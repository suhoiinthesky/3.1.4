package com.exampleForVano.VanoVpered.configs;


import com.exampleForVano.VanoVpered.Service.UserServiceIpm;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceIpm userService;
    private final CoderConfig coderConfig;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceIpm userService, CoderConfig coderConfig) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.coderConfig = coderConfig;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/", "/login", "/h2-console/**", "/index").permitAll()
                        .requestMatchers(h2ConsoleRequestMatcher).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(formLogin -> formLogin
                        .successHandler(successUserHandler)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        http.authenticationProvider(authenticationProvider());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }


    public UserDetailsService getUserDetailsService() {
        return userService;
    }


    public PasswordEncoder getPasswordEncoder() {
        return coderConfig.getPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getUserDetailsService());
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }
    /**
     * @Bean
     *   public WebSecurityCustomizer webSecurityCustomizer() {
     *     return (web) -> web.ignoring().requestMatchers("/js/**", "/images/**");
     *   }
     *   Для тебя Вано. Сюда кинешь файлы с JS-ом
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}