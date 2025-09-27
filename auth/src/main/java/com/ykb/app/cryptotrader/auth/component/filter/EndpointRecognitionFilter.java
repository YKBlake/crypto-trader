package com.ykb.app.cryptotrader.auth.component.filter;

import com.ykb.app.cryptotrader.auth.exceptions.EndpointNotFoundException;
import com.ykb.app.cryptotrader.auth.service.EndpointService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EndpointRecognitionFilter extends HeaderFilter {

    private final EndpointService endpointService;

    public EndpointRecognitionFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache, EndpointService endpointService) {
        super(authenticationEntryPoint, requestCache);
        this.endpointService=endpointService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!endpointService.exists(httpRequest.getRequestURI(), HttpMethod.valueOf(httpRequest.getMethod())))
            throw new EndpointNotFoundException("Endpoint do not exists: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }

}
