package com.ykb.app.cryptotrader.auth.config;

import com.ykb.app.cryptotrader.auth.component.filter.EndpointRecognitionFilter;
import com.ykb.app.cryptotrader.auth.component.filter.JwtFilter;
import com.ykb.app.cryptotrader.auth.component.handler.E401Handler;
import com.ykb.app.cryptotrader.auth.component.handler.E403Handler;
import com.ykb.app.cryptotrader.auth.service.EndpointService;
import com.ykb.app.cryptotrader.auth.service.UserOperationsService;
import com.ykb.app.cryptotrader.data.model.RequestUri;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class AuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, EndpointService endpointService, JwtFilter jwtFilter, EndpointRecognitionFilter endpointRecognitionFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        for (RequestUri uri : endpointService.get()) {
            if (uri.isSecure())
                http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(uri.getHttpMethod().name(), uri.getUri()).authenticated()
                );
            else
                http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(uri.getHttpMethod().name(), uri.getUri()).permitAll()
                );
        }
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new E401Handler())
                        .accessDeniedHandler(new E403Handler())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(endpointRecognitionFilter, JwtFilter.class)
                .sessionManagement(sm -> sm
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserOperationsService userService) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService);
        return builder.build();
    }

}
