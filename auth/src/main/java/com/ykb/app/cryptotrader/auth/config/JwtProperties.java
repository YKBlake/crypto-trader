package com.ykb.app.cryptotrader.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String refreshSecret,
        String accessSecret,
        Duration refreshExpireDuration,
        Duration accessExpireDuration
) {
    public SignatureAlgorithm signatureAlgorithm() {
        return SignatureAlgorithm.HS512;
    }

    public SecretKey refreshKey() {
        return Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey accessKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    }
}