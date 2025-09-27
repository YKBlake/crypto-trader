package com.ykb.app.cryptotrader.domain.trade.strategy;

import com.ykb.app.cryptotrader.data.model.Strategy;
import com.ykb.app.cryptotrader.data.model.StrategySettings;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.operation.PulseVWAPOperation;
import com.ykb.app.cryptotrader.domain.trade.strategy.operation.StrategyOperation;

public class StrategyOperator {

    private final StrategyOperation strategyOperation;

    public StrategyOperator(Strategy strategy, BinanceApi api) {
        StrategySettings settings = strategy.getSettings();
        strategyOperation = switch (strategy.getName()) {
            case PULSE_VWAP -> new PulseVWAPOperation(settings, api);
        };
    }

    public StrategySignal retrieveSignal() {
        return strategyOperation.execute();
    }

}
