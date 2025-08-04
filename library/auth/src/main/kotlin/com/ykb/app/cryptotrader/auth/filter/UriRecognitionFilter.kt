package com.ykb.app.cryptotrader.auth.filter

import org.springframework.http.HttpMethod
import com.ykb.app.cryptotrader.utils.exceptions.NotFoundException
import com.ykb.app.cryptotrader.auth.service.UriSecurityManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class UriRecognitionFilter(
    private val uriSecurityManager: UriSecurityManager
) : HeaderFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (!uriSecurityManager.doUriExist(request.requestURI, HttpMethod.valueOf(request.method)))
                throw NotFoundException("Uri do not exists: '${request.requestURI}'")
            chain.doFilter(request, response)
        } catch (ex: Exception) {
            handleException(request, response, chain, ex)
        }
    }
}