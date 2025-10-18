package com.ykb.app.cryptotrader.domain.trade.strategy;

import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.operation.PulseVWAPOperation;
import com.ykb.app.cryptotrader.domain.trade.strategy.operation.StrategyOperation;
import com.ykb.app.cryptotrader.domain.trade.strategy.settings.PulseVWAPSettings;
import com.ykb.app.cryptotrader.utils.enums.StrategyNames;

public class StrategyOperator {

    private final StrategyOperation<?> strategyOperation;

    public StrategyOperator(StrategyNames strategyName, String jsonSettings, BinanceApi api) {
        strategyOperation = switch (strategyName) {
            case PULSE_VWAP -> new PulseVWAPOperation(new PulseVWAPSettings(jsonSettings), api);
        };
    }

    public StrategySignal retrieveSignal() {
        return strategyOperation.execute();
    }

}
