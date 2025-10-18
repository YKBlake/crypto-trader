package com.ykb.app.cryptotrader.auth.component.filter;

import com.ykb.app.cryptotrader.auth.exceptions.EndpointNotFoundException;
import com.ykb.app.cryptotrader.auth.service.EndpointService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class EndpointRecognitionFilter extends OncePerRequestFilter {

    private final EndpointService endpointService;

    public EndpointRecognitionFilter(EndpointService endpointService) {
        this.endpointService=endpointService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!endpointService.exists(request.getRequestURI(), HttpMethod.valueOf(request.getMethod())))
            throw new EndpointNotFoundException("Endpoint do not exists: " + request.getMethod() + " " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }

}
