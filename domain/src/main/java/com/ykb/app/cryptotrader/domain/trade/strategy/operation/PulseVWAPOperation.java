package com.ykb.app.cryptotrader.domain.trade.strategy.operation;

import com.ykb.app.cryptotrader.data.model.StrategySettings;
import com.ykb.app.cryptotrader.domain.binance.BinanceApi;
import com.ykb.app.cryptotrader.domain.trade.strategy.StrategySignal;

public class PulseVWAPOperation extends StrategyOperation {

    public PulseVWAPOperation(StrategySettings settings, BinanceApi api) {
        super(settings, api);
    }

    @Override
    public StrategySignal execute() {
        return null;
    }

}
