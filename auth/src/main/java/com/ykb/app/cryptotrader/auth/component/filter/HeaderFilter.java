package com.ykb.app.cryptotrader.auth.component.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.RequestCache;

import java.io.IOException;

public abstract class HeaderFilter extends HttpFilter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final RequestCache requestCache;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public HeaderFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache) {
        this.authenticationEntryPoint=authenticationEntryPoint;
        this.requestCache=requestCache;
    }

    protected final void handleException(HttpServletRequest request, HttpServletResponse response, Exception reason) throws ServletException, IOException {
        var ex = reason;
        if(!(ex instanceof AuthenticationException)) {
            ex = new AuthenticationServiceException("Unexpected error occurred", ex);
        }

        var context = securityContextHolderStrategy.createEmptyContext();
        securityContextHolderStrategy.setContext(context);
        requestCache.saveRequest(request, response);
        authenticationEntryPoint.commence(request, response, (AuthenticationException) ex);
    }

}
