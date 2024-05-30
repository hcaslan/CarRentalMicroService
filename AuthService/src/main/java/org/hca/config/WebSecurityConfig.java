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

import java.util.Collections;

import static org.hca.constant.EndPoints.*;


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
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/", "/index.html", "/static/**", "/api/auth/**").permitAll();
                    auth.requestMatchers(ROOT + AUTH + LOGIN, ROOT + AUTH + REGISTER, ROOT + AUTH + CONFIRM).permitAll();
                    auth.requestMatchers(LOGIN, REGISTER,"/welcome",CONFIRM).permitAll();
                    auth.requestMatchers(ROOT + AUTH + FIND_ALL, ROOT + AUTH + FIND_BY_ID).hasAuthority(AppUserRole.ADMIN.name());
                    auth.anyRequest().permitAll();
                })
//                .formLogin(formLogin -> {
//                    formLogin.loginPage(LOGIN).permitAll();
//                    formLogin.defaultSuccessUrl("/welcome", true);
//                })
//                .logout(LogoutConfigurer::permitAll)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .authenticationProvider(authProvider());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        ProviderManager authenticationManager = new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
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
