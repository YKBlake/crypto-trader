package com.ykb.app.cryptotrader.auth.config

import com.ykb.app.cryptotrader.auth.filter.JwtFilter
import com.ykb.app.cryptotrader.auth.filter.UriRecognitionFilter
import com.ykb.app.cryptotrader.auth.service.AuthExceptionHandler
import com.ykb.app.cryptotrader.auth.service.UriSecurityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

abstract class BaseAuthConfig {

    @Autowired
    protected lateinit var jwtFilter: JwtFilter

    @Autowired
    protected lateinit var uriSecurityManager: UriSecurityManager

    @Autowired
    protected lateinit var uriRecognitionFilter: UriRecognitionFilter

    @Autowired
    protected lateinit var exceptionHandler: AuthExceptionHandler

    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        for (uri in uriSecurityManager.getUris()) {
            if (uri.isSecure())
                http.authorizeHttpRequests {
                    it.requestMatchers(uri.getHttpMethod().name(), uri.getUri()).authenticated()
                }
            else
                http.authorizeHttpRequests {
                    it.requestMatchers(uri.getHttpMethod().name(), uri.getUri()).permitAll()
                }
        }
        http.authorizeHttpRequests {
            it.anyRequest().authenticated()
        }
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .exceptionHandling {
                it
                    .authenticationEntryPoint(exceptionHandler)
//                    .accessDeniedHandler(exceptionHandler)
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(uriRecognitionFilter, JwtFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

}