package org.iq47.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService service;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/users/**").permitAll()
                .and().authorizeRequests()
                .antMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER").and().httpBasic()
                .and().authorizeRequests().anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                /*.and()
                .formLogin()
                .and()
                .httpBasic();*/
    }

    @Override
    public UserDetailsService userDetailsService() {
        return service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

/*
http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/v1/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
        .antMatchers("/api/v1/users/create").permitAll()
        .antMatchers("/api/v1/cities/create").hasAuthority("ROLE_ADMIN")
        .antMatchers("/api/v1/cities/delete").hasAuthority("ROLE_ADMIN")
        .antMatchers("/api/v1/cities/**").permitAll()
        .antMatchers("/api/v1/seller_tickets/**").hasAuthority("ROLE_ADMIN")
        .antMatchers("/api/v1/tickets/find").permitAll()
        .antMatchers("/api/v1/tickets/average_price").permitAll()
        .antMatchers("/api/v1/tickets/**").hasAuthority("ROLE_ADMIN")
        .antMatchers("/api/v1/stats").hasAuthority("ROLE_ADMIN")
        .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();*/
