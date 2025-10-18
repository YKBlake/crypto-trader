package com.ykb.app.cryptotrader.auth.service;

import com.ykb.app.cryptotrader.auth.config.JwtProperties;
import com.ykb.app.cryptotrader.auth.dto.JwtDto;
import com.ykb.app.cryptotrader.data.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtService {

    private final UserOperationsService userService;
    private final JwtProperties properties;
    private final JwtParser refreshParser;
    private final JwtParser accessParser;

    public JwtService(UserOperationsService userService, JwtProperties properties) {
        this.userService = userService;
        this.properties = properties;
        this.refreshParser = Jwts.parserBuilder()
                .setSigningKey(properties.refreshKey())
                .build();
        this.accessParser = Jwts.parserBuilder()
                .setSigningKey(properties.accessKey())
                .build();
    }

    public String authenticateRefreshToken(String token) {
        return authenticateToken(token, refreshParser);
    }

    public String authenticateAccessToken(String token) {
        return authenticateToken(token, accessParser);
    }

    public JwtDto generateAuthenticationTokens(User user) {
        return new JwtDto(
                generateJwtToken(user, properties.refreshKey(), properties.refreshExpireDuration()),
                generateAccessToken(user)
        );
    }

    public String generateAccessToken(User user) {
        return generateJwtToken(user, properties.accessKey(), properties.accessExpireDuration());
    }

    private String generateJwtToken(User user, SecretKey key, Duration expireDuration) {
        Map<String, Object> claims = new ConcurrentHashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(new Date().toInstant().plus(expireDuration)))
                .signWith(key, properties.signatureAlgorithm())
                .compact();
    }

    private String authenticateToken(String token, JwtParser parser) {
        var claims = parser.parseClaimsJws(token).getBody();
        var username = claims.getSubject();
        userService.loadUserByUsername(username);
        claims.getExpiration().before(new Date());
        return username;
    }

}
