package com.ykb.app.cryptotrader.auth.filter

import com.ykb.app.cryptotrader.utils.exceptions.auth.InternalServerErrorException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.savedrequest.RequestCache

abstract class HeaderFilter : HttpFilter() {

    @Autowired
    private lateinit var authenticationEntryPoint: AuthenticationEntryPoint

    @Autowired
    private lateinit var requestCache: RequestCache

    private val securityContextHolderStrategy: SecurityContextHolderStrategy? = SecurityContextHolder.getContextHolderStrategy()

    protected fun handleException(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, reason: Exception) {
        var ex = reason
        ex.printStackTrace()
        if(ex !is AuthenticationException) {
            var message = ex.message
            if(message.isNullOrEmpty())
                message = "Unexpected error occurred"
            ex = InternalServerErrorException(message)
        }

        val context: SecurityContext = this.securityContextHolderStrategy!!.createEmptyContext()
        this.securityContextHolderStrategy.context = context
        this.requestCache.saveRequest(request, response)
        this.authenticationEntryPoint.commence(request, response, ex)
    }

}