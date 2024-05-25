package org.hca.config;

import lombok.AllArgsConstructor;
import org.hca.entity.enums.AppUserRole;
import org.hca.filter.JwtFilter;
import org.hca.service.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v*/auth/**").permitAll();
                    auth.requestMatchers("/api/v*/greetings/user").hasAuthority(AppUserRole.USER.name());
                    auth.requestMatchers("/api/v*/greetings/admin").hasAuthority(AppUserRole.ADMIN.name());
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .authenticationProvider(authProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
        authenticationManager.setAuthenticationEventPublisher(authenticationEventPublisher());
        return authenticationManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

    @Bean
    public AuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService;
    }
}
