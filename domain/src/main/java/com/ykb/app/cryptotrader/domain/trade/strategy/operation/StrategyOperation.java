package com.ykb.app.cryptotrader.domain.trade.strategy.operation;

import com.ykb.app.cryptotrader.data.model.StrategySettings;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;

public abstract class StrategyOperation {

    protected final StrategySettings settings;
    protected final BinanceApi api;

    public StrategyOperation(StrategySettings settings, BinanceApi api) {
        this.settings = settings;
        this.api = api;
    }

    public abstract StrategySignal execute();

}
