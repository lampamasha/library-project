package ru.itgirl.libraryproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/author/create").hasAuthority("USER")
                        .requestMatchers("/author/update").hasAuthority("ADMIN")
                        .requestMatchers("/author/delete/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/book/create").hasAuthority("ADMIN")
                        .requestMatchers("/book/update").hasAuthority("ADMIN")
                        .requestMatchers("/book/delete/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/book/v1").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .csrf().disable()
                .httpBasic();
        return httpSecurity.build();
    }
}
