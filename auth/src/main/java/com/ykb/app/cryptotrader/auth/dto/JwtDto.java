package com.ykb.app.cryptotrader.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    private String refreshToken;
    private String accessToken;
}
