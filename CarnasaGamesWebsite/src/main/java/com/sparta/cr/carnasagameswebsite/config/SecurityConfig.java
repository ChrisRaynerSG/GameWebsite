package com.sparta.cr.carnasagameswebsite.config;

import com.sparta.cr.carnasagameswebsite.service.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SiteUserService siteUserService;

    @Autowired
    SecurityConfig(SiteUserService siteUserService){
        this.siteUserService = siteUserService;
    }

    @Bean
    SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/index","/","/login","/register").permitAll()
                .requestMatchers("/auth/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/secure/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .permitAll()
                                .failureForwardUrl("/login")
                                .defaultSuccessUrl("/auth/home",true)
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .permitAll())
                .userDetailsService(siteUserService).build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
