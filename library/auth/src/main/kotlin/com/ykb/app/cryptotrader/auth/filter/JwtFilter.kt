package com.ykb.app.cryptotrader.auth.filter

import org.springframework.http.HttpMethod
import com.ykb.app.cryptotrader.data.auth.model.User
import com.ykb.app.cryptotrader.auth.service.UserManager
import com.ykb.app.cryptotrader.auth.service.JwtManager
import com.ykb.app.cryptotrader.auth.service.UriSecurityManager
import com.ykb.app.cryptotrader.utils.logger.Logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.stereotype.Component

@Component
class JwtFilter(
    private val userManager: UserManager,
    private val jwtManager: JwtManager,
    private val uriSecurityManager: UriSecurityManager,
    authenticationEntryPoint: AuthenticationEntryPoint,
    requestCache: RequestCache,
    @Value("\${app.property.auth.url.login}") private val loginUri: String
) : HeaderFilter(authenticationEntryPoint, requestCache) {

    private val log: Logger = Logger.getLogger(JwtFilter::class)
    private val authCookieName = "Authorization"

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if(authentication != null && authentication.isAuthenticated) {
                chain.doFilter(request, response)
                return
            }

            val isLoginView = uriSecurityManager.isLoginView(request.requestURI, HttpMethod.valueOf(request.method.uppercase()))
            if(isLoginView) {
                chain.doFilter(request, response)
                return
            }

            val isView = uriSecurityManager.isView(request.requestURI, HttpMethod.valueOf(request.method))
            if(isView) {
                val jwt = getRefreshToken(request)
                if(!jwt.isNullOrBlank())
                    if(authenticateWithRefreshToken(jwt, request))
                        chain.doFilter(request, response)
                    else
                        response.sendRedirect(loginUri)
                else
                    response.sendRedirect(loginUri)
                return
            }

            val jwt = getAccessToken(request)
            if(!jwt.isNullOrBlank())
                authenticateWithAccessToken(jwt, request)
            chain.doFilter(request, response)
        } catch(ex: Exception) {
            handleException(request, response, chain, ex)
        }
    }

    private fun authenticateWithAccessToken(jwt: String, request: HttpServletRequest): Boolean {
        try {
            if (SecurityContextHolder.getContext().authentication == null && jwtManager.validateAccessToken(jwt)) {
                val username = jwtManager.getUsernameFromAccessToken(jwt)
                authenticateJwt(username!!, request)
                return true
            }
            return false
        } catch(ex: Exception) {
            return false
        }
    }

    private fun authenticateWithRefreshToken(jwt: String, request: HttpServletRequest): Boolean {
        try {
            if (SecurityContextHolder.getContext().authentication == null && jwtManager.validateRefreshToken(jwt)) {
                val username = jwtManager.getUsernameFromRefreshToken(jwt)
                authenticateJwt(username!!, request)
                return true
            }
            return false
        } catch(ex: Exception) {
            return false
        }
    }

    private fun authenticateJwt(username: String, request: HttpServletRequest) {
        val user: User = userManager.loadUserByUsername(username)
        val usernamePasswordAuthToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        usernamePasswordAuthToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthToken
    }

    private fun getRefreshToken(request: HttpServletRequest): String? {
        return try {
            request.cookies.first { cookie -> authCookieName==cookie.name }.value
        } catch (ex: Exception) {
            null
        }
    }

    private fun getAccessToken(request: HttpServletRequest): String? {
        var jwt: String? = null
        val requestTokenHeader = request.getHeader(authCookieName)
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwt = requestTokenHeader.substring(7)
        }
        return jwt
    }

}