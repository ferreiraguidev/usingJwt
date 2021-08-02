package com.example.usingjwt.config;

import com.example.usingjwt.security.CustomUserDetailService;
import com.example.usingjwt.security.JWTAuthenticationFilter;
import com.example.usingjwt.security.JWTAuthorizationFilter;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableWebSecurity
@Builder
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*/professor/**").hasRole("PROFESSOR")
                .antMatchers("/*/student/**").hasRole("STUDENT")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),customUserDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
