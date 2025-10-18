package com.ykb.app.cryptotrader.domain.trade.strategy.settings;

public abstract class StrategySettings {

    public StrategySettings(String jsonString) {
        loadJsonString(jsonString);
    }

    protected abstract void loadJsonString(String jsonString);

    public abstract String toJsonString();

}
