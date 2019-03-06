package com.example;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private FirebaseAuth firebaseAuth;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().authorizeRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated()
                .and().logout()
                .and().csrf().disable()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), firebaseAuth))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
