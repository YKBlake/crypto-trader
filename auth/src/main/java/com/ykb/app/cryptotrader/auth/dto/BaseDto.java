package com.ykb.app.cryptotrader.auth.dto;

import lombok.Getter;

public abstract class BaseDto {

    @Getter
    private final Fault fault;

    public BaseDto(String code, String description) {
        fault = new Fault(code, description);
    }

    record Fault(String code, String description) {}

}
