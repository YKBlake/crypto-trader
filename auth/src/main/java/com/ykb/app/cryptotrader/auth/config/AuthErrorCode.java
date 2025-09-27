package com.ykb.app.cryptotrader.auth.config;

public enum AuthErrorCode {
    E500_1("Unexpected error occurred");

    private final String description;

    AuthErrorCode(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

}
