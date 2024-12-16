package com.twitter.backend.config;

import com.twitter.backend.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfiguration {

    @Autowired
    private UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(customizer -> customizer.disable());  // Disables csrf but also removes all auth.

        httpSecurity.authorizeHttpRequests(request -> request.anyRequest().authenticated());  // Enabling authentication for everything. Not csrf. But this denies the login page as well

        // httpSecurity.formLogin(Customizer.withDefaults());  // we remove authentication for form login page.

        httpSecurity.httpBasic(Customizer.withDefaults());  // We also remove authentication for basic http from postman

        // This makes our session stateless means no more session time and we have separate session with Id for every request means no more CSRF issue.
        // but this will make it ask for creds for every page every time, Because it is a new session everytime. So remove form Login on line 21. But httpBasic from line 22 will still give you pop up for creds
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
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
}
