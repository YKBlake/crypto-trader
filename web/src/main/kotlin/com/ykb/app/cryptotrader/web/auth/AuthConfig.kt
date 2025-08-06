package com.ykb.app.cryptotrader.web.auth

import com.ykb.app.cryptotrader.auth.base.BaseAuthConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class AuthConfig : BaseAuthConfig() {

    @Bean
    override fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return super.filterChain(http)
    }

}