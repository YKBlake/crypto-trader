package com.ykb.app.cryptotrader.web.auth

import com.ykb.app.cryptotrader.auth.config.BaseAuthConfig
import com.ykb.app.cryptotrader.auth.filter.JwtFilter
import com.ykb.app.cryptotrader.auth.filter.UriRecognitionFilter
import com.ykb.app.cryptotrader.auth.service.AuthExceptionHandler
import com.ykb.app.cryptotrader.auth.service.UriSecurityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class AuthConfig : BaseAuthConfig() {

    @Bean
    override fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
        return super.filterChain(http)
    }

}