package com.twitter.backend.config;

import com.twitter.backend.filter.JwtFilter;
import com.twitter.backend.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class securityConfiguration {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())                                  // Disables csrf but also removes all auth.
                .authorizeHttpRequests(request -> request
                        .requestMatchers("twitter/register", "twitter/login").permitAll()         // Permits given resources without authentication
                        .anyRequest().authenticated())                                                 // Enabling authentication for everything. Not csrf. But this denies the login page as well
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())                                                  // We also remove authentication for basic http from postman
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))                // No session is maintained mitigating the CSRF issue.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)                // jwtFilter will get called right before calling the usernamePasswordAuthenticationFilter
                .build();
    }

    /**
     * Using below bean we provide a AuthenticationProvider, UserDetailsService
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));    // No password encoding
        provider.setUserDetailsService(userDetailService);    // We will pass our own User detail service to this config.

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Using this below bean the given usernames & passwords get added to in memory user details manager for spring security.
     * But we will be taking our users from DB so this we will not use this is just FYI
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("username")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails user0 = User
//                .withDefaultPasswordEncoder()
//                .username("username-0")
//                .password("password-0")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, user0);
//    }
}
