package com.ykb.app.cryptotrader.auth.service

import com.ykb.app.cryptotrader.utils.exceptions.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.*
import org.springframework.security.authentication.password.CompromisedPasswordException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.security.web.authentication.rememberme.CookieTheftException
import org.springframework.security.web.authentication.rememberme.InvalidCookieException
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.security.web.authentication.www.NonceExpiredException
import org.springframework.stereotype.Component

@Component
class AuthExceptionHandler : AuthenticationEntryPoint/*, AccessDeniedHandlerImpl()*/ {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        var message = authException!!.message
        if(message.isNullOrEmpty())
            message="Unexpected error occurred"
        enrichResponse(response!!, getStatusCode(authException), message)
    }

//    override fun handle(
//        request: HttpServletRequest?,
//        response: HttpServletResponse?,
//        accessDeniedException: AccessDeniedException?
//    ) {
//        recognizeUri(request, response, accessDeniedException)
//    }

    private fun getStatusCode(authException: AuthenticationException): Int {
        return when(authException) {
            is NotFoundException
                -> HttpStatus.NOT_FOUND.value()
            is AccountExpiredException,
            is LockedException,
            is DisabledException,
            is CredentialsExpiredException
                -> HttpStatus.FORBIDDEN.value()
            is AuthenticationCredentialsNotFoundException,
            is CompromisedPasswordException,
            is CookieTheftException,
            is InsufficientAuthenticationException,
            is InvalidCookieException,
            is PreAuthenticatedCredentialsNotFoundException,
            is NonceExpiredException,
            is UsernameNotFoundException,
            is SessionAuthenticationException,
            is BadCredentialsException
                -> HttpStatus.UNAUTHORIZED.value()
            is InternalAuthenticationServiceException,
            is ProviderNotFoundException
                -> HttpStatus.INTERNAL_SERVER_ERROR.value()
            else -> HttpStatus.FORBIDDEN.value()
        }
    }

    private fun enrichResponse(response: HttpServletResponse, status: Int, message: String) {
        response.status = status
        response.writer.write(message)
        response.writer.flush()
        response.writer.close()
    }
}