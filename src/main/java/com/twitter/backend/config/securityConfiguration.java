package com.twitter.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfiguration {

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
}
