package com.ykb.app.cryptotrader.web.dto;

import lombok.Getter;

public abstract sealed class BaseDto permits FaultDto {

    @Getter
    private final Fault fault;

    public BaseDto(String code, String description) {
        fault = new Fault(code, description);
    }

    record Fault(String code, String description) {}

}
