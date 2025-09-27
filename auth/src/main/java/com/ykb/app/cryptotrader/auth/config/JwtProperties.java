package com.ykb.app.cryptotrader.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        SignatureAlgorithm signatureAlgorithm,
        SecretKeySpec refreshSecret,
        SecretKeySpec accessSecret,
        long refreshExpireTime,
        long accessExpireTime
) {

    public JwtProperties(String refreshSecret, String accessSecret, String refreshExpireTime, String accessExpireTime) {
        this(
                SignatureAlgorithm.HS512,
                new SecretKeySpec(refreshSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName()),
                new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName()),
                Long.parseLong(refreshExpireTime),
                Long.parseLong(accessExpireTime)
        );
    }

}
