package com.ykb.app.cryptotrader.domain.trade.strategy.operation;

import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;
import com.ykb.app.cryptotrader.domain.trade.strategy.settings.StrategySettings;

public abstract class StrategyOperation<T extends StrategySettings> {

    private final T settings;
    protected final BinanceApi binanceApi;

    public StrategyOperation(T settings, BinanceApi binanceApi) {
        this.settings = settings;
        this.binanceApi = binanceApi;
    }

    public abstract StrategySignal execute();

    protected T getSettings() {
        return settings;
    }

}
