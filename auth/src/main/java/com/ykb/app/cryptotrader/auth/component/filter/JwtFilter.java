package com.ykb.app.cryptotrader.auth.component.filter;

import com.ykb.app.cryptotrader.auth.service.EndpointService;
import com.ykb.app.cryptotrader.auth.service.JwtService;
import com.ykb.app.cryptotrader.auth.service.UserOperationsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;
    private final UserOperationsService userService;
    private final EndpointService endpointService;

    public JwtFilter(JwtService jwtService, UserOperationsService userService, EndpointService endpointService) {
        super("");
        this.jwtService=jwtService;
        this.userService=userService;
        this.endpointService = endpointService;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.isAuthenticated())
            return false;
        return endpointService.isAuthenticated(request.getRequestURI(), HttpMethod.valueOf(request.getMethod().toUpperCase()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        var isView = endpointService.isView(request.getRequestURI(), HttpMethod.valueOf(request.getMethod()));
        if(isView)
            return authenticateWithRefreshToken(request);

        return authenticateWithAccessToken(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private Authentication authenticateWithRefreshToken(HttpServletRequest request) {
        var jwt = getRefreshToken(request);
        String username = jwtService.authenticateRefreshToken(jwt);
        return authenticate(username, request);
    }

    private Authentication authenticateWithAccessToken(HttpServletRequest request) {
        var jwt = getAccessToken(request);
        String username = jwtService.authenticateAccessToken(jwt);
        return authenticate(username, request);
    }

    private Authentication authenticate(String username, HttpServletRequest request) {
        var user = userService.loadUserByUsername(username);
        var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthToken;
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie token = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("Authorization"))
                .findFirst().orElse(null);
        return token!=null ? token.getValue() : null;
    }

    private String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer "))
            return token.substring(7);

        return null;
    }

}
