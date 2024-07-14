package com.getreadyforthenext.core.common.config;

import com.getreadyforthenext.core.user.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${oauth.successUrl}")
    String successUrl;

    @Value("${oauth.failureUrl}")
    String failureUrl;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.oauth2Login(oauth2LoginCustomizer -> {
            oauth2LoginCustomizer.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService()));
            oauth2LoginCustomizer.successHandler(this::successHandler);
            oauth2LoginCustomizer.failureHandler(this::failureHandler);
        });

        httpSecurity.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }));

        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/").permitAll();
            authorizeHttpRequests.requestMatchers("/oauth2/authorization/google").permitAll();
            authorizeHttpRequests.requestMatchers("/actuator/**").hasRole(Role.ADMIN.toString());
            authorizeHttpRequests.anyRequest().authenticated();
        });

        return httpSecurity.build();
    }

    @Bean
    public DefaultOAuth2UserService oAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    private void successHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.sendRedirect(this.successUrl + "?accessToken=" + "token");
    }

    private void failureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.sendRedirect(this.failureUrl);
    }
}
