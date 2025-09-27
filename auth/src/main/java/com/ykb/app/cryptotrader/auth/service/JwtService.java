package com.ykb.app.cryptotrader.auth.service;

import com.ykb.app.cryptotrader.auth.config.JwtProperties;
import com.ykb.app.cryptotrader.auth.dto.JwtDto;
import com.ykb.app.cryptotrader.data.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
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
                .setSigningKey(properties.refreshSecret())
                .build();
        this.accessParser = Jwts.parserBuilder()
                .setSigningKey(properties.accessSecret())
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
                generateJwtToken(user, properties.refreshSecret(), properties.refreshExpireTime()),
                generateAccessToken(user)
        );
    }

    public String generateAccessToken(User user) {
        return generateJwtToken(user, properties.accessSecret(), properties.accessExpireTime());
    }

    private String generateJwtToken(User user, SecretKeySpec key, long expireTime) {
        Map<String, Object> claims = new ConcurrentHashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
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
