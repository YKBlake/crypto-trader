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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserOperationsService userService;
    private final EndpointService endpointService;

    public JwtFilter(JwtService jwtService, UserOperationsService userService, EndpointService endpointService) {
        this.jwtService=jwtService;
        this.userService=userService;
        this.endpointService = endpointService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }

            if (requiresAuthentication(request)) {
                Authentication auth = attemptAuthentication(request);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.isAuthenticated())
            return false;
        return endpointService.isAuthenticated(request.getRequestURI(), HttpMethod.valueOf(request.getMethod().toUpperCase()));
    }

    private Authentication attemptAuthentication(HttpServletRequest request) {
        var isView = endpointService.isView(request.getRequestURI(), HttpMethod.valueOf(request.getMethod().toUpperCase()));
        if(isView)
            return authenticateWithRefreshToken(request);

        return authenticateWithAccessToken(request);
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
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        Cookie token = Arrays.stream(cookies)
                .filter(c -> "Authorization".equals(c.getName()))
                .findFirst()
                .orElse(null);
        return token != null ? token.getValue() : null;
    }

    private String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer "))
            return token.substring(7);

        return null;
    }

}
